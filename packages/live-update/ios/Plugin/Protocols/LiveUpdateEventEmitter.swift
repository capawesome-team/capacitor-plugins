import Foundation
import Capacitor

@objc public protocol LiveUpdateEventEmitter {
    func onDownloadBundleProgress(_ event: DownloadBundleProgressEvent)
    func onNextBundleSet(_ event: NextBundleSetEvent)
    func onReloaded()
}
