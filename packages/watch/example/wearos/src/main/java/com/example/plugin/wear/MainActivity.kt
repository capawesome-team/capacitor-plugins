package com.example.plugin.wear

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import io.capawesome.capacitorjs.plugins.watch.sdk.CapawesomeWatch
import kotlin.random.Random
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainActivity : Activity() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private lateinit var watch: CapawesomeWatch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        watch = CapawesomeWatch(this)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val statusTextView = TextView(this)
        statusTextView.text = "Ready"
        layout.addView(statusTextView)

        layout.addView(createButton("Send Message") {
            watch.sendMessage(JSONObject().put("text", "Hello from the watch!"))
        })
        layout.addView(createButton("Send Message (Reply)") {
            val reply = watch.sendMessageForReply(JSONObject().put("text", "Hello from the watch!"))
            statusTextView.text = reply.toString()
        })
        layout.addView(createButton("Transfer User Info") {
            watch.transferUserInfo(JSONObject().put("sentAt", System.currentTimeMillis()))
        })
        layout.addView(createButton("Update State") {
            watch.updateState(JSONObject().put("counter", Random.nextInt(100)))
        })

        val scrollView = ScrollView(this)
        scrollView.addView(layout)
        setContentView(scrollView)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private fun createButton(text: String, onClick: suspend () -> Unit): Button {
        val button = Button(this)
        button.text = text
        button.setOnClickListener {
            scope.launch {
                try {
                    onClick()
                } catch (exception: Exception) {
                    Log.e(TAG, "Action failed.", exception)
                }
            }
        }
        return button
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
