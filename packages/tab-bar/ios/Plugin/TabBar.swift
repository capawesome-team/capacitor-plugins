import Foundation
import Capacitor
import UIKit

@objc public class TabBar: NSObject, UITabBarDelegate {
    private let plugin: TabBarPlugin
    private let tabBarView = TabBarView()
    private var tabs: [Tab] = []
    private var webViewFrameObservation: NSKeyValueObservation?
    private var windowObserverView: WindowObserverView?

    init(plugin: TabBarPlugin, config: TabBarConfig?) {
        self.plugin = plugin
        super.init()
        setUpTabBarView()
        observeWebViewFrame()
        guard let config = config else {
            return
        }
        applyColors(config.colors)
        if let tabsOptions = config.tabs {
            applyTabs(tabsOptions)
            attachTabBarView()
        }
    }

    @objc public func hide(completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            self.detachTabBarView()
            completion(nil)
        }
    }

    @objc public func selectTabById(_ options: SelectTabByIdOptions, completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            guard let index = self.indexOfTab(withId: options.id) else {
                completion(CustomError.tabNotFound)
                return
            }
            self.tabBarView.selectedItem = self.tabBarView.items?[index]
            completion(nil)
        }
    }

    @objc public func setColors(_ options: SetColorsOptions, completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            self.applyColors(options)
            completion(nil)
        }
    }

    @objc public func setTabs(_ options: SetTabsOptions, completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            self.applyTabs(options)
            completion(nil)
        }
    }

    @objc public func show(completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            if self.tabs.isEmpty {
                completion(CustomError.tabsNotSet)
                return
            }
            self.attachTabBarView()
            completion(nil)
        }
    }

    public func tabBar(_ tabBar: UITabBar, didSelect item: UITabBarItem) {
        plugin.notifyTabSelectedListeners(TabSelectedEvent(id: tabs[item.tag].id))
    }

    private func applyColors(_ options: SetColorsOptions) {
        tabBarView.tintColor = options.selectedColor
        tabBarView.unselectedItemTintColor = options.unselectedColor
    }

    private func applyTabs(_ options: SetTabsOptions) {
        let previouslySelectedTabId = tabBarView.selectedItem.map { tabs[$0.tag].id }
        tabs = options.tabs
        tabBarView.items = tabs.enumerated().map { index, tab in
            let item = UITabBarItem(title: tab.title, image: tab.image, tag: index)
            item.badgeValue = tab.badge
            return item
        }
        let selectedIndex = indexOfTab(withId: options.selectedTabId) ?? indexOfTab(withId: previouslySelectedTabId) ?? 0
        tabBarView.selectedItem = tabBarView.items?[selectedIndex]
    }

    private func attachTabBarView() {
        guard tabBarView.superview == nil, windowObserverView == nil, let webView = plugin.bridge?.webView else {
            return
        }
        // Never add the tab bar to the web view itself, as its safe area would then depend on the inset it causes.
        guard let host = webView.superview else {
            attachTabBarViewWhenInWindow(webView)
            return
        }
        host.addSubview(tabBarView)
        NSLayoutConstraint.activate([
            tabBarView.leadingAnchor.constraint(equalTo: host.leadingAnchor),
            tabBarView.trailingAnchor.constraint(equalTo: host.trailingAnchor),
            tabBarView.bottomAnchor.constraint(equalTo: host.bottomAnchor)
        ])
    }

    private func attachTabBarViewWhenInWindow(_ webView: UIView) {
        let observerView = WindowObserverView()
        observerView.isHidden = true
        observerView.onMoveToWindow = { [weak self] in
            self?.windowObserverView = nil
            self?.attachTabBarView()
        }
        windowObserverView = observerView
        webView.addSubview(observerView)
    }

    private func detachTabBarView() {
        windowObserverView?.removeFromSuperview()
        windowObserverView = nil
        tabBarView.removeFromSuperview()
        plugin.bridge?.viewController?.additionalSafeAreaInsets.bottom = 0
    }

    private func indexOfTab(withId id: String?) -> Int? {
        return tabs.firstIndex { $0.id == id }
    }

    private func observeWebViewFrame() {
        // Resizing the web view (e.g. by a keyboard plugin) does not lay out the tab bar, so the inset must be recomputed.
        webViewFrameObservation = plugin.bridge?.webView?.observe(\.frame) { [weak self] _, _ in
            self?.tabBarView.setNeedsLayout()
        }
    }

    private func setUpTabBarView() {
        tabBarView.delegate = self
        tabBarView.translatesAutoresizingMaskIntoConstraints = false
        tabBarView.onLayoutSubviews = { [weak self] in
            self?.updateSafeAreaInsets()
        }
        if #available(iOS 26.0, *), let scrollView = plugin.bridge?.webView?.scrollView {
            let interaction = UIScrollEdgeElementContainerInteraction()
            interaction.scrollView = scrollView
            interaction.edge = .bottom
            tabBarView.addInteraction(interaction)
        }
    }

    private func updateSafeAreaInsets() {
        guard tabBarView.window != nil, let webView = plugin.bridge?.webView, let viewController = plugin.bridge?.viewController else {
            return
        }
        let tabBarFrame = tabBarView.convert(tabBarView.bounds, to: webView)
        let tabBarTopFromBottom = webView.bounds.height - tabBarFrame.minY
        let naturalBottomInset = webView.safeAreaInsets.bottom - viewController.additionalSafeAreaInsets.bottom
        let additionalBottomInset = max(0, tabBarTopFromBottom - naturalBottomInset)
        if abs(additionalBottomInset - viewController.additionalSafeAreaInsets.bottom) > 0.5 {
            viewController.additionalSafeAreaInsets.bottom = additionalBottomInset
        }
    }
}
