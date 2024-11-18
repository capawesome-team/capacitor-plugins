import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(AppReviewPlugin)
public class AppReviewPlugin: CAPPlugin {
    public let reviewRequestNotAvailable = "Review request is not available on this device."
    public let bundleIdError = "Could not open App Store because the bundle ID could not be determined."

    private var implementation: AppReview?

    override public func load() {
        self.implementation = AppReview(plugin: self)
    }

    @objc func openAppStore(_ call: CAPPluginCall) {
        self.implementation?.openAppStore(completion: { error in
            if let error = error {
                call.reject(error.localizedDescription)
            } else {
                call.resolve()
            }
        })

        call.resolve()
    }

    @objc func requestReview(_ call: CAPPluginCall) {
        self.implementation?.requestReview(completion: { error in
            if let error = error {
                call.reject(error.localizedDescription)
                return
            } else {
                call.resolve()
            }
        })
    }
}
