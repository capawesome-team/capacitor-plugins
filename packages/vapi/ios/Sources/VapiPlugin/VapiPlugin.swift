import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(VapiPlugin)
public class VapiPlugin: CAPPlugin, CAPBridgedPlugin {
    public let errorApiKeyMissing = "apiKey must be provided."
    
    public let identifier = "VapiPlugin"
    public let jsName = "Vapi"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "isMuted", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setup", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = VapiImpl()
    
    @objc func isMuted(_ call: CAPPluginCall) {
        Task {
            do {
                try await implementation.isMuted()
            } catch {
                rejectCall(call, error)
            }
        }
    }

    @objc func setup(_ call: CAPPluginCall) {
        guard let apiKey = call.getString("apiKey") else {
            call.reject(errorApiKeyMissing)
            return
        }
        
        let options = SetupOptions(apiKey: apiKey)
        
        implementation.setup(options)
        call.resolve()
    }
    
    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        var message = error.localizedDescription
        switch error {
        case let CustomError.uninitialized:
            message = "Vapi is not initialized. Please call setup() first."
        default:
            break
        }
        call.reject(error.localizedDescription, nil, error)
    }
}
