import Foundation
import Capacitor

@objc(PdfViewerPlugin)
public class PdfViewerPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "PdfViewerPlugin"
    public let jsName = "PdfViewer"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "close", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "open", returnType: CAPPluginReturnPromise)
    ]

    public static let eventClosed = "closed"
    public static let eventPageChange = "pageChange"
    public static let tag = "PdfViewerPlugin"

    private var implementation: PdfViewer?

    @objc func close(_ call: CAPPluginCall) {
        implementation?.close(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        })
    }

    override public func load() {
        self.implementation = PdfViewer(plugin: self)
    }

    @objc public func notifyClosedListeners() {
        self.notifyListeners(Self.eventClosed, data: [:])
    }

    @objc public func notifyPageChangeListeners(_ event: PageChangeEvent) {
        self.notifyListeners(Self.eventPageChange, data: event.toJSObject() as? [String: Any])
    }

    @objc func open(_ call: CAPPluginCall) {
        do {
            let options = try OpenOptions(call)

            implementation?.open(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", PdfViewerPlugin.tag, "] ", error)
        let code = (error as? CustomError)?.code
        call.reject(error.localizedDescription, code)
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }
}
