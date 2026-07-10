import Foundation
import Capacitor
import VDARSDK

class CapacitorARViewController: VDARLiveAnnotationViewController {

    weak var pixlive: Pixlive?

    override func annotationViewDidBecomeEmpty() {
        super.annotationViewDidBecomeEmpty()
        if let bridge = findBridge(from: view.window?.rootViewController) {
            bridge.setStatusBarVisible(true)
        }
        pixlive?.annotationViewDidBecomeEmpty()
    }

    override func annotationViewDidPresentAnnotations() {
        super.annotationViewDidPresentAnnotations()
        if let bridge = findBridge(from: view.window?.rootViewController) {
            bridge.setStatusBarVisible(false)
        }
        pixlive?.annotationViewDidPresentAnnotations()
    }
    
    private func findBridge(from root: UIViewController?) -> CAPBridgeViewController? {
        guard let root else { return nil }
        if let b = root as? CAPBridgeViewController { return b }
        for child in root.children {
            if let found = findBridge(from: child) { return found }
        }
        if let presented = root.presentedViewController,
           let found = findBridge(from: presented) { return found }
        return nil
    }
}
