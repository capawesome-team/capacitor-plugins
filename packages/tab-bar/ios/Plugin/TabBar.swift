import Foundation
import Capacitor
import UIKit

@objc public class TabBar: NSObject, UITabBarControllerDelegate {
    private let plugin: TabBarPlugin
    private let tabBarController = UITabBarController()
    private var tabs: [Tab] = []

    init(plugin: TabBarPlugin, config: TabBarConfig?) {
        self.plugin = plugin
        super.init()
        tabBarController.delegate = self
        embedTabBarController()
        applyTabs(config?.tabs?.tabs ?? [], selectedTabId: config?.tabs?.selectedTabId)
        setTabBarHidden(tabs.isEmpty)
        if let colors = config?.colors {
            applyColors(colors)
        }
    }

    @objc public func hide(completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            self.setTabBarHidden(true)
            completion(nil)
        }
    }

    @objc public func selectTabById(_ options: SelectTabByIdOptions, completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            guard let index = self.indexOfTab(withId: options.id) else {
                completion(CustomError.tabNotFound)
                return
            }
            self.tabBarController.selectedIndex = index
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
            self.applyTabs(options.tabs, selectedTabId: options.selectedTabId)
            completion(nil)
        }
    }

    @objc public func show(completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            if self.tabs.isEmpty {
                completion(CustomError.tabsNotSet)
                return
            }
            self.setTabBarHidden(false)
            completion(nil)
        }
    }

    public func tabBarController(_ tabBarController: UITabBarController, didSelect viewController: UIViewController) {
        guard let index = tabBarController.viewControllers?.firstIndex(of: viewController), tabs.indices.contains(index) else {
            return
        }
        plugin.notifyTabSelectedListeners(TabSelectedEvent(id: tabs[index].id))
    }

    private var selectedTabId: String? {
        let index = tabBarController.selectedIndex
        return tabs.indices.contains(index) ? tabs[index].id : nil
    }

    private func applyColors(_ options: SetColorsOptions) {
        tabBarController.tabBar.tintColor = options.selectedColor
        tabBarController.tabBar.unselectedItemTintColor = options.unselectedColor
    }

    private func applyTabs(_ newTabs: [Tab], selectedTabId: String?) {
        guard let webView = plugin.bridge?.webView else {
            return
        }
        let previouslySelectedTabId = self.selectedTabId
        tabs = newTabs
        // The web view always needs a content view controller, even without tabs.
        let contentViewControllers = tabs.isEmpty ? [TabContentViewController(webView: webView)] : tabs.map { tab in
            let viewController = TabContentViewController(webView: webView)
            viewController.tabBarItem = UITabBarItem(title: tab.title, image: tab.image, tag: 0)
            viewController.tabBarItem.badgeValue = tab.badge
            return viewController
        }
        tabBarController.setViewControllers(contentViewControllers, animated: false)
        tabBarController.selectedIndex = indexOfTab(withId: selectedTabId) ?? indexOfTab(withId: previouslySelectedTabId) ?? 0
    }

    /// Replaces the view of the bridge view controller, which is the web view itself, with a container
    /// that hosts the tab bar controller. The web view becomes the content of the selected tab.
    /// Plugins are loaded before the view of the bridge view controller is added to a window.
    private func embedTabBarController() {
        guard let viewController = plugin.bridge?.viewController, let webView = plugin.bridge?.webView else {
            return
        }
        let container = UIView()
        container.backgroundColor = webView.backgroundColor
        viewController.view = container
        viewController.addChild(tabBarController)
        tabBarController.view.frame = container.bounds
        tabBarController.view.autoresizingMask = [.flexibleWidth, .flexibleHeight]
        container.addSubview(tabBarController.view)
        tabBarController.didMove(toParent: viewController)
    }

    private func indexOfTab(withId id: String?) -> Int? {
        return tabs.firstIndex { $0.id == id }
    }

    private func setTabBarHidden(_ hidden: Bool) {
        // The plugin is only loaded on iOS 18 and later, where hiding also removes the space reserved for the bar.
        guard #available(iOS 18.0, *) else {
            return
        }
        tabBarController.setTabBarHidden(hidden, animated: false)
    }
}
