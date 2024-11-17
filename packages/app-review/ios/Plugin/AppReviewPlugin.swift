import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(AppReviewPlugin)
public class AppReviewPlugin: CAPPlugin {
    private var implementation: AppReview?

    override public func load() {
        self.implementation = AppReview(plugin: self)
    }

    @objc func openAppStore(_ call: CAPPluginCall) {
        guard let appID = call.getString("appID") else {
            call.reject("appID parameter is required")
            return
        }

        CAPLog.print("Calling openAppStore with appID: \(appID)")
        self.implementation?.openAppStore(appID: appID)

        call.resolve()
    }

    @objc func requestReview(_ call: CAPPluginCall) {
        self.implementation?.requestReview()
    }
}
