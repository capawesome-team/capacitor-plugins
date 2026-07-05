/*
  JNI layer between the plugin's Java code and the Node.js engine.

  Based on the `nodejs-mobile-react-native` project
  (MIT licensed, https://github.com/nodejs-mobile/nodejs-mobile-react-native).
*/
#include <jni.h>
#include <string>
#include <cstdlib>
#include <pthread.h>
#include <unistd.h>
#include <android/log.h>

#include "node.h"
#include "bridge.h"

#define LOG_TAG "NodejsPlugin"

// Cache the environment variable for the thread running Node.js to call into Java.
JNIEnv* cacheEnvPointer = NULL;

extern "C"
JNIEXPORT void JNICALL
Java_io_capawesome_capacitorjs_plugins_nodejs_Nodejs_sendMessageToNodeChannel(
        JNIEnv *env,
        jclass /* clazz */,
        jstring channelName,
        jstring msg) {
    const char* nativeChannelName = env->GetStringUTFChars(channelName, 0);
    const char* nativeMessage = env->GetStringUTFChars(msg, 0);
    capacitor_bridge_notify(nativeChannelName, nativeMessage);
    env->ReleaseStringUTFChars(channelName, nativeChannelName);
    env->ReleaseStringUTFChars(msg, nativeMessage);
}

extern "C"
JNIEXPORT void JNICALL
Java_io_capawesome_capacitorjs_plugins_nodejs_Nodejs_registerNodeDataDirPath(
        JNIEnv *env,
        jclass /* clazz */,
        jstring dataDir) {
    const char* nativeDataDir = env->GetStringUTFChars(dataDir, 0);
    capacitor_register_node_data_dir_path(nativeDataDir);
    env->ReleaseStringUTFChars(dataDir, nativeDataDir);
}

void rcv_message(const char* channel_name, const char* msg) {
    JNIEnv *env = cacheEnvPointer;
    if (!env) {
        return;
    }
    jclass cls = env->FindClass("io/capawesome/capacitorjs/plugins/nodejs/Nodejs");
    if (cls != nullptr) {
        jmethodID m_onMessageReceived = env->GetStaticMethodID(cls, "onMessageReceived", "(Ljava/lang/String;Ljava/lang/String;)V");
        if (m_onMessageReceived != nullptr) {
            jstring java_channel_name = env->NewStringUTF(channel_name);
            jstring java_msg = env->NewStringUTF(msg);
            env->CallStaticVoidMethod(cls, m_onMessageReceived, java_channel_name, java_msg);
            env->DeleteLocalRef(java_channel_name);
            env->DeleteLocalRef(java_msg);
        }
    }
    env->DeleteLocalRef(cls);
}

// Threads to redirect stdout and stderr to logcat.
int pipe_stdout[2];
int pipe_stderr[2];
pthread_t thread_stdout;
pthread_t thread_stderr;

void *thread_stderr_func(void*) {
    ssize_t redirect_size;
    char buf[2048];
    while ((redirect_size = read(pipe_stderr[0], buf, sizeof buf - 1)) > 0) {
        // __android_log will add a new line anyway.
        if (buf[redirect_size - 1] == '\n') {
            --redirect_size;
        }
        buf[redirect_size] = 0;
        __android_log_write(ANDROID_LOG_ERROR, LOG_TAG, buf);
    }
    return 0;
}

void *thread_stdout_func(void*) {
    ssize_t redirect_size;
    char buf[2048];
    while ((redirect_size = read(pipe_stdout[0], buf, sizeof buf - 1)) > 0) {
        // __android_log will add a new line anyway.
        if (buf[redirect_size - 1] == '\n') {
            --redirect_size;
        }
        buf[redirect_size] = 0;
        __android_log_write(ANDROID_LOG_INFO, LOG_TAG, buf);
    }
    return 0;
}

int start_redirecting_stdout_stderr() {
    // Set stdout as unbuffered.
    setvbuf(stdout, 0, _IONBF, 0);
    pipe(pipe_stdout);
    dup2(pipe_stdout[1], STDOUT_FILENO);

    // Set stderr as unbuffered.
    setvbuf(stderr, 0, _IONBF, 0);
    pipe(pipe_stderr);
    dup2(pipe_stderr[1], STDERR_FILENO);

    if (pthread_create(&thread_stdout, 0, thread_stdout_func, 0) == -1) {
        return -1;
    }
    pthread_detach(thread_stdout);

    if (pthread_create(&thread_stderr, 0, thread_stderr_func, 0) == -1) {
        return -1;
    }
    pthread_detach(thread_stderr);

    return 0;
}

// Node's libuv requires all arguments being on contiguous memory.
extern "C"
JNIEXPORT jint JNICALL
Java_io_capawesome_capacitorjs_plugins_nodejs_Nodejs_startNodeWithArguments(
        JNIEnv *env,
        jclass /* clazz */,
        jobjectArray arguments,
        jstring nodePath,
        jboolean redirectOutputToLogcat) {

    // Set the NODE_PATH environment variable.
    const char* node_path = env->GetStringUTFChars(nodePath, 0);
    setenv("NODE_PATH", node_path, 1);
    env->ReleaseStringUTFChars(nodePath, node_path);

    jsize argument_count = env->GetArrayLength(arguments);

    // Compute the byte size needed for all arguments in contiguous memory.
    int c_arguments_size = 0;
    for (int i = 0; i < argument_count; i++) {
        jstring argument = (jstring)env->GetObjectArrayElement(arguments, i);
        const char* current_argument = env->GetStringUTFChars(argument, 0);
        c_arguments_size += strlen(current_argument);
        c_arguments_size++; // for '\0'
        env->ReleaseStringUTFChars(argument, current_argument);
        env->DeleteLocalRef(argument);
    }

    // Store the arguments in contiguous memory.
    char* args_buffer = (char*)calloc(c_arguments_size, sizeof(char));

    // The argv to pass into Node.js.
    char* argv[argument_count];

    // Iterate through the expected start position of each argument in args_buffer.
    char* current_args_position = args_buffer;

    // Populate the args_buffer and argv.
    for (int i = 0; i < argument_count; i++) {
        jstring argument = (jstring)env->GetObjectArrayElement(arguments, i);
        const char* current_argument = env->GetStringUTFChars(argument, 0);

        // Copy the current argument to its expected position in args_buffer.
        strncpy(current_args_position, current_argument, strlen(current_argument));

        // Save the current argument start position in argv.
        argv[i] = current_args_position;

        // Increment to the next argument's expected position.
        current_args_position += strlen(current_args_position) + 1;

        env->ReleaseStringUTFChars(argument, current_argument);
        env->DeleteLocalRef(argument);
    }

    capacitor_register_bridge_cb(&rcv_message);

    cacheEnvPointer = env;

    if (redirectOutputToLogcat) {
        if (start_redirecting_stdout_stderr() == -1) {
            __android_log_write(ANDROID_LOG_ERROR, LOG_TAG, "Couldn't start redirecting stdout and stderr to logcat.");
        }
    }

    // Start Node.js, with argc and argv. This call blocks until the
    // Node.js event loop exits.
    return jint(node::Start(argument_count, argv));
}
