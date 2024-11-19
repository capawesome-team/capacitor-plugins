import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(AppReviewPlugin)
public class AppReviewPlugin: CAPPlugin {
    public static let tag = "AppReviewPlugin"

    private var implementation: AppReview?

    override public func load() {
        self.implementation = AppReview(plugin: self)
    }

    @objc func openAppStore(_ call: CAPPluginCall) {
        self.implementation?.openAppStore(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func requestReview(_ call: CAPPluginCall) {
        self.implementation?.requestReview(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }
    
    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", AppReviewPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }
}
