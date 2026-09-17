import Foundation
import Capacitor

@objc(FlicPlugin)
public class FlicPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "FlicPlugin"
    public let jsName = "Flic"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "checkPermissions", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "connectButtonById", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "disconnectButtonById", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "forgetButtonById", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getButtons", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "initialize", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "requestPermissions", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "startScan", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "stopScan", returnType: CAPPluginReturnPromise)
    ]
    public static let eventButtonConnected = "buttonConnected"
    public static let eventButtonConnectionFailed = "buttonConnectionFailed"
    public static let eventButtonDisconnected = "buttonDisconnected"
    public static let eventButtonDoubleClick = "buttonDoubleClick"
    public static let eventButtonDown = "buttonDown"
    public static let eventButtonHold = "buttonHold"
    public static let eventButtonReady = "buttonReady"
    public static let eventButtonSingleClick = "buttonSingleClick"
    public static let eventButtonUnpaired = "buttonUnpaired"
    public static let eventButtonUp = "buttonUp"
    public static let eventScanStatusChanged = "scanStatusChanged"
    public static let tag = "FlicPlugin"

    private var implementation: Flic?

    override public func load() {
        self.implementation = Flic(plugin: self)
    }

    @objc override public func checkPermissions(_ call: CAPPluginCall) {
        implementation?.checkPermissions(completion: { result in
            self.resolveCall(call, result)
        })
    }

    @objc func connectButtonById(_ call: CAPPluginCall) {
        do {
            let options = try ConnectButtonByIdOptions(call)
            implementation?.connectButtonById(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call)
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    @objc func disconnectButtonById(_ call: CAPPluginCall) {
        do {
            let options = try DisconnectButtonByIdOptions(call)
            implementation?.disconnectButtonById(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call)
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    @objc func forgetButtonById(_ call: CAPPluginCall) {
        do {
            let options = try ForgetButtonByIdOptions(call)
            implementation?.forgetButtonById(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call)
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    @objc func getButtons(_ call: CAPPluginCall) {
        implementation?.getButtons(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        })
    }

    @objc func initialize(_ call: CAPPluginCall) {
        let options = InitializeOptions(call)
        implementation?.initialize(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        })
    }

    public func notifyButtonConnectedListeners(_ event: ButtonConnectedEvent) {
        notifyListeners(Self.eventButtonConnected, data: event.toJSObject() as? [String: Any])
    }

    public func notifyButtonConnectionFailedListeners(_ event: ButtonConnectionFailedEvent) {
        notifyListeners(Self.eventButtonConnectionFailed, data: event.toJSObject() as? [String: Any])
    }

    public func notifyButtonDisconnectedListeners(_ event: ButtonDisconnectedEvent) {
        notifyListeners(Self.eventButtonDisconnected, data: event.toJSObject() as? [String: Any])
    }

    public func notifyButtonDoubleClickListeners(_ event: ButtonEvent) {
        notifyListeners(Self.eventButtonDoubleClick, data: event.toJSObject() as? [String: Any])
    }

    public func notifyButtonDownListeners(_ event: ButtonEvent) {
        notifyListeners(Self.eventButtonDown, data: event.toJSObject() as? [String: Any])
    }

    public func notifyButtonHoldListeners(_ event: ButtonEvent) {
        notifyListeners(Self.eventButtonHold, data: event.toJSObject() as? [String: Any])
    }

    public func notifyButtonReadyListeners(_ event: ButtonReadyEvent) {
        notifyListeners(Self.eventButtonReady, data: event.toJSObject() as? [String: Any])
    }

    public func notifyButtonSingleClickListeners(_ event: ButtonEvent) {
        notifyListeners(Self.eventButtonSingleClick, data: event.toJSObject() as? [String: Any])
    }

    public func notifyButtonUnpairedListeners(_ event: ButtonUnpairedEvent) {
        notifyListeners(Self.eventButtonUnpaired, data: event.toJSObject() as? [String: Any])
    }

    public func notifyButtonUpListeners(_ event: ButtonEvent) {
        notifyListeners(Self.eventButtonUp, data: event.toJSObject() as? [String: Any])
    }

    public func notifyScanStatusChangedListeners(_ event: ScanStatusChangedEvent) {
        notifyListeners(Self.eventScanStatusChanged, data: event.toJSObject() as? [String: Any])
    }

    @objc override public func requestPermissions(_ call: CAPPluginCall) {
        implementation?.requestPermissions(completion: { result in
            self.resolveCall(call, result)
        })
    }

    @objc func startScan(_ call: CAPPluginCall) {
        implementation?.startScan(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        })
    }

    @objc func stopScan(_ call: CAPPluginCall) {
        implementation?.stopScan(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        })
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", FlicPlugin.tag, "] ", error)
        let code = (error as? CustomError)?.code
        call.reject(error.localizedDescription, code)
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
