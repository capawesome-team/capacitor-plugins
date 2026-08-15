package com.example.plugin.wear

import android.util.Log
import io.capawesome.capacitorjs.plugins.watch.sdk.WatchListenerService
import org.json.JSONObject

class ExampleWatchListenerService : WatchListenerService() {
    override fun onMessageReceived(data: JSONObject, reply: ((JSONObject) -> Unit)?) {
        Log.d(TAG, "Message received: $data")
        reply?.invoke(JSONObject().put("text", "Hello from the watch!"))
    }

    override fun onStateReceived(data: JSONObject) {
        Log.d(TAG, "State received: $data")
    }

    override fun onUserInfoReceived(data: JSONObject) {
        Log.d(TAG, "User info received: $data")
    }

    companion object {
        private const val TAG = "ExampleService"
    }
}
