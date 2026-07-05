import Foundation
import Capacitor

@objc(NodejsPlugin)
public class NodejsPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "NodejsPlugin"
    public let jsName = "Nodejs"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "isReady", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "send", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "start", returnType: CAPPluginReturnPromise)
    ]

    public static let eventMessage = "message"
    public static let eventReady = "ready"
    public static let startModeAuto = "auto"
    public static let startModeManual = "manual"
    public static let tag = "NodejsPlugin"

    private var config = NodejsConfig()
    private var implementation: Nodejs?

    override public func load() {
        config = getNodejsConfig()
        implementation = Nodejs(plugin: self, config: config)
        if config.startMode == NodejsPlugin.startModeAuto {
            implementation?.start(StartOptions()) { error in
                if let error = error {
                    CAPLog.print("[", NodejsPlugin.tag, "] ", error)
                }
            }
        }
    }

    @objc func isReady(_ call: CAPPluginCall) {
        let result = IsReadyResult(ready: implementation?.isReady() == true)
        resolveCall(call, result)
    }

    @objc func send(_ call: CAPPluginCall) {
        do {
            let options = try SendOptions(call)

            try implementation?.send(options)
            resolveCall(call)
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func start(_ call: CAPPluginCall) {
        guard config.startMode == NodejsPlugin.startModeManual else {
            rejectCall(call, CustomError.startModeNotManual)
            return
        }
        let options = StartOptions(call)

        implementation?.start(options) { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        }
    }

    func notifyMessageListeners(_ event: MessageEvent) {
        notifyListeners(NodejsPlugin.eventMessage, data: event.toJSObject() as? JSObject ?? JSObject(), retainUntilConsumed: true)
    }

    func notifyReadyListeners() {
        notifyListeners(NodejsPlugin.eventReady, data: JSObject(), retainUntilConsumed: true)
    }

    private func getNodejsConfig() -> NodejsConfig {
        var config = NodejsConfig()
        if let nodeDir = getConfig().getString("nodeDir") {
            config.nodeDir = nodeDir
        }
        if let startMode = getConfig().getString("startMode") {
            config.startMode = startMode
        }
        return config
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", NodejsPlugin.tag, "] ", error)
        call.reject(error.localizedDescription, (error as? CustomError)?.code)
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
