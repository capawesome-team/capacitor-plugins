import Foundation
import Capacitor
import ObjectiveC.runtime

@objc public class HomeIndicator: NSObject {
    fileprivate static var hidden = false

    private static var didInstallOverride = false

    let plugin: HomeIndicatorPlugin

    init(plugin: HomeIndicatorPlugin) {
        self.plugin = plugin
        super.init()
        Self.installOverrideIfNeeded()
    }

    @objc public func hide(completion: @escaping (Error?) -> Void) {
        setHidden(true, completion: completion)
    }

    @objc public func isHidden(completion: @escaping (IsHiddenResult?, Error?) -> Void) {
        let result = IsHiddenResult(hidden: Self.hidden)
        completion(result, nil)
    }

    @objc public func show(completion: @escaping (Error?) -> Void) {
        setHidden(false, completion: completion)
    }

    private static func installOverrideIfNeeded() {
        guard !didInstallOverride else {
            return
        }
        didInstallOverride = true
        let originalSelector = #selector(getter: UIViewController.prefersHomeIndicatorAutoHidden)
        let swizzledSelector = #selector(getter: CAPBridgeViewController.homeIndicatorAutoHiddenOverride)
        guard let swizzledMethod = class_getInstanceMethod(CAPBridgeViewController.self, swizzledSelector) else {
            return
        }
        let didAddMethod = class_addMethod(
            CAPBridgeViewController.self,
            originalSelector,
            method_getImplementation(swizzledMethod),
            method_getTypeEncoding(swizzledMethod)
        )
        if !didAddMethod, let originalMethod = class_getInstanceMethod(CAPBridgeViewController.self, originalSelector) {
            method_exchangeImplementations(originalMethod, swizzledMethod)
        }
    }

    private func setHidden(_ hidden: Bool, completion: @escaping (Error?) -> Void) {
        Self.hidden = hidden
        DispatchQueue.main.async {
            self.plugin.bridge?.viewController?.setNeedsUpdateOfHomeIndicatorAutoHidden()
            completion(nil)
        }
    }
}

extension CAPBridgeViewController {
    @objc fileprivate var homeIndicatorAutoHiddenOverride: Bool {
        return HomeIndicator.hidden
    }
}
