import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(SquareMobilePaymentsPlugin)
public class SquareMobilePaymentsPlugin: CAPPlugin, CAPBridgedPlugin {
    public static let tag = "SquareMobilePayments"

    public let identifier = "SquareMobilePaymentsPlugin"
    public let jsName = "SquareMobilePayments"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "initialize", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "authorize", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isAuthorized", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "deauthorize", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "showSettings", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getSettings", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "startPairing", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "stopPairing", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isPairingInProgress", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getReaders", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "forgetReader", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "retryConnection", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "startPayment", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "cancelPayment", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getAvailableCardInputMethods", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "checkPermissions", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "requestPermissions", returnType: CAPPluginReturnPromise)
    ]

    public static let permissionLocation = "location"
    public static let permissionBluetooth = "bluetooth"

    private var implementation: SquareMobilePayments?

    override public func load() {
        self.implementation = SquareMobilePayments(plugin: self)
    }

    @objc func initialize(_ call: CAPPluginCall) {
        do {
            let options = try InitializeOptions(call)

            try implementation?.initialize(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func authorize(_ call: CAPPluginCall) {
        do {
            let options = try AuthorizeOptions(call)

            try implementation?.authorize(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func isAuthorized(_ call: CAPPluginCall) {
        do {
            try implementation?.isAuthorized(completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func deauthorize(_ call: CAPPluginCall) {
        do {
            try implementation?.deauthorize(completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func showSettings(_ call: CAPPluginCall) {
        do {
            try implementation?.showSettings(completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getSettings(_ call: CAPPluginCall) {
        do {
            try implementation?.getSettings(completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func startPairing(_ call: CAPPluginCall) {
        do {
            try implementation?.startPairing(completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func stopPairing(_ call: CAPPluginCall) {
        do {
            try implementation?.stopPairing(completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func isPairingInProgress(_ call: CAPPluginCall) {
        do {
            try implementation?.isPairingInProgress(completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getReaders(_ call: CAPPluginCall) {
        do {
            try implementation?.getReaders(completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func forgetReader(_ call: CAPPluginCall) {
        do {
            let options = try ForgetReaderOptions(call)

            try implementation?.forgetReader(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func retryConnection(_ call: CAPPluginCall) {
        do {
            let options = try RetryConnectionOptions(call)

            try implementation?.retryConnection(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func startPayment(_ call: CAPPluginCall) {
        do {
            let options = try StartPaymentOptions(call)

            try implementation?.startPayment(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func cancelPayment(_ call: CAPPluginCall) {
        do {
            try implementation?.cancelPayment(completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getAvailableCardInputMethods(_ call: CAPPluginCall) {
        do {
            try implementation?.getAvailableCardInputMethods(completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc override public func checkPermissions(_ call: CAPPluginCall) {
        var locationResult: String
        switch implementation?.checkLocationPermission() {
        case .notDetermined:
            locationResult = "prompt"
        case .none, .restricted, .denied:
            locationResult = "denied"
        case .authorizedAlways, .authorizedWhenInUse:
            locationResult = "granted"
        @unknown default:
            locationResult = "prompt"
        }

        var bluetoothResult: String
        switch implementation?.checkBluetoothPermission() {
        case .notDetermined:
            bluetoothResult = "prompt"
        case .none, .restricted, .denied:
            bluetoothResult = "denied"
        case .allowedAlways:
            bluetoothResult = "granted"
        @unknown default:
            bluetoothResult = "prompt"
        }

        var result = JSObject()
        result[SquareMobilePaymentsPlugin.permissionLocation] = locationResult
        result[SquareMobilePaymentsPlugin.permissionBluetooth] = bluetoothResult
        call.resolve(result)
    }

    @objc override public func requestPermissions(_ call: CAPPluginCall) {
        if !hasUsageDescription(forKey: "NSLocationWhenInUseUsageDescription") {
            rejectCall(call, CustomError.privacyDescriptionsMissing)
            return
        }
        if !hasUsageDescription(forKey: "NSBluetoothAlwaysUsageDescription") {
            rejectCall(call, CustomError.privacyDescriptionsMissing)
            return
        }

        let group = DispatchGroup()

        if implementation?.checkLocationPermission() == .notDetermined {
            group.enter()
            implementation?.requestLocationPermission { _ in
                group.leave()
            }
        }

        if implementation?.checkBluetoothPermission() == .notDetermined {
            group.enter()
            implementation?.requestBluetoothPermission {
                group.leave()
            }
        }

        group.notify(queue: DispatchQueue.main) {
            self.checkPermissions(call)
        }
    }

    // MARK: - Helper Methods

    private func hasUsageDescription(forKey key: String) -> Bool {
        return Bundle.main.object(forInfoDictionaryKey: key) is String
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", SquareMobilePaymentsPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }

    private func resolveCall<T: Result>(_ call: CAPPluginCall, _ result: T?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
