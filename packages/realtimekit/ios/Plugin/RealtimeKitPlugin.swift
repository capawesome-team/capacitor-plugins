import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(RealtimeKitPlugin)
public class RealtimeKitPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "RealtimeKitPlugin"
    public let jsName = "RealtimeKit"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "initialize", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "startMeeting", returnType: CAPPluginReturnPromise)
    ]
    public static let tag = "RealtimeKit"

    private let implementation = RealtimeKit()

    @objc func initialize(_ call: CAPPluginCall) {
        if !hasUsageDescription(forKey: "NSBluetoothPeripheralUsageDescription") {
            rejectCall(call, CustomError.privacyDescriptionsMissing)
            return
        }
        if !hasUsageDescription(forKey: "NSBluetoothAlwaysUsageDescription") {
            rejectCall(call, CustomError.privacyDescriptionsMissing)
            return
        }
        if !hasUsageDescription(forKey: "NSCameraUsageDescription") {
            rejectCall(call, CustomError.privacyDescriptionsMissing)
            return
        }
        if !hasUsageDescription(forKey: "NSMicrophoneUsageDescription") {
            rejectCall(call, CustomError.privacyDescriptionsMissing)
            return
        }
        if !hasUsageDescription(forKey: "NSPhotoLibraryUsageDescription") {
            rejectCall(call, CustomError.privacyDescriptionsMissing)
            return
        }
        if !hasUsageDescription(forKey: "UIBackgroundModes") {
            rejectCall(call, CustomError.privacyDescriptionsMissing)
            return
        }

        resolveCall(call)
    }

    @objc func startMeeting(_ call: CAPPluginCall) {
        do {
            let options = StartMeetingOptions(call)
            implementation.startMeeting(options) { error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                resolveCall(call)
            }
        } catch {
            rejectCall(call, error)
        }
    }

    private func hasUsageDescription(forKey key: String) -> Bool {
        return Bundle.main.object(forInfoDictionaryKey: key) is String
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", RealtimeKitPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }
}
