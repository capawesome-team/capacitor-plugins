import UIKit

class TabBarView: UITabBar {
    var onLayoutSubviews: (() -> Void)?

    override func layoutSubviews() {
        super.layoutSubviews()
        onLayoutSubviews?()
    }

    override func safeAreaInsetsDidChange() {
        super.safeAreaInsetsDidChange()
        // The intrinsic height of `UITabBar` includes its bottom safe area, but UIKit does not invalidate it when the safe area changes.
        invalidateIntrinsicContentSize()
    }
}
