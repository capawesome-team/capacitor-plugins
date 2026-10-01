import UIKit

class WindowObserverView: UIView {
    var onMoveToWindow: (() -> Void)?

    override func didMoveToWindow() {
        super.didMoveToWindow()
        guard window != nil else {
            return
        }
        removeFromSuperview()
        onMoveToWindow?()
    }
}
