/*
  Implements the bridge APIs between the plugin's native layer and the
  Node.js engine.

  Based on the bridge of the `nodejs-mobile-react-native` project
  (MIT licensed, https://github.com/nodejs-mobile/nodejs-mobile-react-native).
*/
#include "node.h"
#include "uv.h"
#include "bridge.h"

#include <map>
#include <mutex>
#include <queue>
#include <string>
#include <cstring>
#include <cstdlib>

void FlushMessageQueue(uv_async_t* handle);
class Channel;

std::mutex channelsMutex;
std::map<std::string, Channel*> channels;

/**
 * Channel class. Messages sent from the native layer to the Node.js
 * engine are queued per channel and delivered on the main libuv loop
 * thread.
 */
class Channel {
private:
    v8::Isolate* isolate = nullptr;
    v8::Persistent<v8::Function> function;
    uv_async_t* queue_uv_handle = nullptr;
    std::mutex uvhandleMutex;
    std::mutex queueMutex;
    std::queue<char*> messageQueue;
    std::string name;
    bool initialized = false;

public:
    Channel(std::string name) : name(name) {};

    // Set up the channel's V8 data. This method can be called
    // only once per channel.
    void setV8Function(v8::Isolate* isolate, v8::Local<v8::Function> func) {
        this->isolate = isolate;
        this->function.Reset(isolate, func);
        this->uvhandleMutex.lock();
        if (this->queue_uv_handle == nullptr) {
            this->queue_uv_handle = (uv_async_t*)malloc(sizeof(uv_async_t));
            uv_async_init(uv_default_loop(), this->queue_uv_handle, FlushMessageQueue);
            this->queue_uv_handle->data = (void*)this;
            initialized = true;
            uv_async_send(this->queue_uv_handle);
        } else {
            isolate->ThrowException(v8::Exception::TypeError(
                v8::String::NewFromUtf8(isolate, "Channel already exists.").ToLocalChecked()
            ));
        }
        this->uvhandleMutex.unlock();
    };

    // Add a new message to the channel's queue and notify libuv to
    // call us back to do the actual message delivery.
    void queueMessage(char* msg) {
        this->queueMutex.lock();
        this->messageQueue.push(msg);
        this->queueMutex.unlock();

        if (initialized) {
            uv_async_send(this->queue_uv_handle);
        }
    };

    // Process one message at the time, to simplify synchronization
    // between threads and minimize lock retention.
    void flushQueue() {
        char* message = nullptr;
        bool empty = true;

        this->queueMutex.lock();
        if (!(this->messageQueue.empty())) {
            message = this->messageQueue.front();
            this->messageQueue.pop();
            empty = this->messageQueue.empty();
        }
        this->queueMutex.unlock();

        if (message != nullptr) {
            this->invokeNodeListener(message);
            free(message);
        }

        if (!empty) {
            uv_async_send(this->queue_uv_handle);
        }
    };

    // Calls into Node.js to execute the registered listener.
    // This method is always executed on the main libuv loop thread.
    void invokeNodeListener(char* msg) {
        v8::HandleScope scope(isolate);

        v8::Local<v8::Function> node_function = v8::Local<v8::Function>::New(isolate, function);
        v8::Local<v8::Value> global = isolate->GetCurrentContext()->Global();

        v8::Local<v8::String> channel_name = v8::String::NewFromUtf8(isolate, this->name.c_str(), v8::NewStringType::kNormal).ToLocalChecked();
        v8::Local<v8::String> message = v8::String::NewFromUtf8(isolate, msg, v8::NewStringType::kNormal).ToLocalChecked();

        const int argc = 2;
        v8::Local<v8::Value> argv[argc] = { channel_name, message };

        v8::MaybeLocal<v8::Value> result = node_function->Call(isolate->GetCurrentContext(), global, argc, argv);
        (void)result;
    };
};

char* datadir_path = nullptr;

void capacitor_register_node_data_dir_path(const char* path) {
    size_t pathLength = strlen(path);
    datadir_path = (char*)calloc(sizeof(char), pathLength + 1);
    strncpy(datadir_path, path, pathLength);
}

capacitor_bridge_cb embedder_callback = nullptr;

void capacitor_register_bridge_cb(capacitor_bridge_cb cb) {
    embedder_callback = cb;
}

Channel* GetOrCreateChannel(std::string channelName) {
    channelsMutex.lock();
    Channel* channel = nullptr;
    auto it = channels.find(channelName);
    if (it != channels.end()) {
        channel = it->second;
    } else {
        channel = new Channel(channelName);
        channels[channelName] = channel;
    }
    channelsMutex.unlock();
    return channel;
};

void FlushMessageQueue(uv_async_t* handle) {
    Channel* channel = (Channel*)handle->data;
    channel->flushQueue();
}

void Method_RegisterChannel(const v8::FunctionCallbackInfo<v8::Value>& args) {
    v8::Isolate* isolate = args.GetIsolate();
    if (args.Length() != 2) {
        isolate->ThrowException(v8::Exception::TypeError(
            v8::String::NewFromUtf8(isolate, "Wrong number of arguments.").ToLocalChecked()
        ));
        return;
    }

    v8::String::Utf8Value channel_name(isolate, args[0]);
    std::string channel_name_str(*channel_name);

    if (!args[1]->IsFunction()) {
        isolate->ThrowException(v8::Exception::TypeError(
            v8::String::NewFromUtf8(isolate, "Expected a function.").ToLocalChecked()
        ));
        return;
    }

    v8::Local<v8::Function> listener = v8::Local<v8::Function>::Cast(args[1]);

    Channel* channel = GetOrCreateChannel(channel_name_str);
    channel->setV8Function(isolate, listener);
}

void Method_SendMessage(const v8::FunctionCallbackInfo<v8::Value>& args) {
    v8::Isolate* isolate = args.GetIsolate();
    if (args.Length() != 2) {
        isolate->ThrowException(v8::Exception::TypeError(
            v8::String::NewFromUtf8(isolate, "Wrong number of arguments.").ToLocalChecked()
        ));
        return;
    }

    v8::String::Utf8Value channel_name(isolate, args[0]);
    std::string channel_name_str(*channel_name);

    v8::String::Utf8Value message(isolate, args[1]);
    std::string message_str(*message);

    if (embedder_callback) {
        embedder_callback(channel_name_str.c_str(), message_str.c_str());
    }
}

void Method_GetDataDir(const v8::FunctionCallbackInfo<v8::Value>& args) {
    v8::Isolate* isolate = args.GetIsolate();
    if (datadir_path == nullptr) {
        isolate->ThrowException(v8::Exception::TypeError(
            v8::String::NewFromUtf8(isolate, "Data directory not set from native side.").ToLocalChecked()
        ));
        return;
    }

    v8::Local<v8::String> return_datadir = v8::String::NewFromUtf8(isolate, datadir_path, v8::NewStringType::kNormal).ToLocalChecked();
    args.GetReturnValue().Set(return_datadir);
}

void Init(v8::Local<v8::Object> exports) {
    NODE_SET_METHOD(exports, "sendMessage", Method_SendMessage);
    NODE_SET_METHOD(exports, "registerChannel", Method_RegisterChannel);
    NODE_SET_METHOD(exports, "getDataDir", Method_GetDataDir);
}

void capacitor_bridge_notify(const char* channelName, const char* message) {
    size_t messageLength = strlen(message);
    char* messageCopy = (char*)calloc(sizeof(char), messageLength + 1);
    strncpy(messageCopy, message, messageLength);

    Channel* channel = GetOrCreateChannel(std::string(channelName));
    channel->queueMessage(messageCopy);
}

NODE_MODULE_LINKED(capacitor_bridge, Init)
