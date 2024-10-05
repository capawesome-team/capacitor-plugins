package io.capawesome.capacitorjs.plugins.vapi

import ai.vapi.android.Vapi
import io.capawesome.capacitorjs.plugins.vapi.classes.options.SetupOptions

class VapiImpl(private val plugin: VapiPlugin) {
    fun setup(options: SetupOptions) {
        val apiKey = options.apiKey

        val configuration = Vapi.Configuration(apiKey, "api.vapi.ai")
        val vapi = Vapi(plugin.context, plugin.activity.lifecycle, configuration)
        vapi.eventFlow.collect { event ->
            when (event) {
                is Vapi.Event.CallDidStart -> println("Call started")
                is Vapi.Event.CallDidEnd -> println("Call ended")
                is Vapi.Event.Transcript -> println("Transcript: ${event.text}")
                is Vapi.Event.FunctionCall -> println("Function call: ${event.name}, parameters: ${event.parameters}")
                is Vapi.Event.SpeechUpdate -> println("Speech update: ${event.text}")
                is Vapi.Event.Metadata -> println("Metadata: ${event.data}")
                is Vapi.Event.ConversationUpdate -> println("Conversation update: ${event.messages}")
                is Vapi.Event.Hang -> println("Hang event received")
                is Vapi.Event.Error -> println("Error: ${event.error}")
            }
        }
    }
}
