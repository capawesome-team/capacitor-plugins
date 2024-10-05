import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(VapiPlugin)
public class VapiPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "VapiPlugin"
    public let jsName = "Vapi"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "isMuted", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "say", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setMuted", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setup", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "start", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "stop", returnType: CAPPluginReturnPromise)
    ]
    private var implementation: VapiImpl?

    override public func load() {
        implementation = VapiImpl(plugin: self)
    }

    @objc func isMuted(_ call: CAPPluginCall) {
        Task {
            do {
                try await implementation?.isMuted()
            } catch {
                rejectCall(call, error)
            }
        }
    }

    @objc func say(_ call: CAPPluginCall) {
        Task {
            do {
                guard let message = call.getString("message") else {
                    rejectCall(call, CustomError.messageMissing)
                    return
                }

                let options = SayOptions(message: message)

                try await implementation?.say(options)
                call.resolve()
            } catch {
                rejectCall(call, error)
            }
        }
    }

    @objc func setMuted(_ call: CAPPluginCall) {
        Task {
            do {
                guard let muted = call.getBool("muted") else {
                    rejectCall(call, CustomError.mutedMissing)
                    return
                }

                let options = SetMutedOptions(muted: muted)

                try await implementation?.setMuted(options)
                call.resolve()
            } catch {
                rejectCall(call, error)
            }
        }
    }

    @objc func setup(_ call: CAPPluginCall) {
        guard let apiKey = call.getString("apiKey") else {
            rejectCall(call, CustomError.apiKeyMissing)
            return
        }

        let options = SetupOptions(apiKey: apiKey)

        implementation?.setup(options)
        call.resolve()
    }

    @objc func start(_ call: CAPPluginCall) {
        Task {
            do {
                guard let assistantId = call.getString("assistantId") else {
                    rejectCall(call, CustomError.assistantIdMissing)
                    return
                }

                let options = StartOptions(assistantId: assistantId)

                try await implementation?.start(options)
                call.resolve()
            } catch {
                rejectCall(call, error)
            }
        }
    }

    @objc func stop(_ call: CAPPluginCall) {
        Task {
            do {
                try await implementation?.stop()
                call.resolve()
            } catch {
                rejectCall(call, error)
            }
        }
    }
    
    func notifyCallEndListeners() {
        notifyListeners("callEnd", data: nil)
    }
    
    func notifyCallStartListeners() {
        notifyListeners("callStart", data: nil)
    }
    
    func notifyConversationUpdateListeners(event: ConversationUpdateEvent) {
        if let event = event.toJSObject() as? JSObject {
            notifyListeners("conversationUpdate", data: event)
        }
    }
    
    func notifyErrorListeners(event: ErrorEvent) {
        if let event = event.toJSObject() as? JSObject {
            notifyListeners("error", data: event)
        }
    }
    
    func notifySpeechUpdateListeners(event: SpeechUpdateEvent) {
        if let event = event.toJSObject() as? JSObject {
            notifyListeners("speechUpdate", data: event)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        var message = error.localizedDescription
        switch error {
        case let CustomError.apiKeyMissing:
            message = "apiKey must be provided."
        case let CustomError.assistantIdMissing:
            message = "assistantId must be provided."
        case let CustomError.customError(msg):
            message = msg
        case let CustomError.messageMissing:
            message = "message must be provided."
        case let CustomError.mutedMissing:
            message = "muted must be provided."
        case let CustomError.uninitialized:
            message = "Vapi is not initialized. Please call setup() first."
        default:
            break
        }
        call.reject(message, nil, error)
    }
}
