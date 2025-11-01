import Foundation
import UIKit
import Capacitor

@objc public class ScreenOrientation: NSObject {
    static var supportedInterfaceOrientations = UIInterfaceOrientationMask.all
    private let plugin: ScreenOrientationPlugin
    private var currentOrientationType: String?
    private var lastOrientationType: String?

    init(plugin: ScreenOrientationPlugin) {
        self.plugin = plugin
        super.init()
        NotificationCenter.default.addObserver(self, selector: #selector(self.handleOrientationChange), name: UIDevice.orientationDidChangeNotification, object: nil)
    }

    @objc public static func getSupportedInterfaceOrientations() -> UIInterfaceOrientationMask {
        return ScreenOrientation.supportedInterfaceOrientations
    }

    @objc public func lock(_ orientationType: String?, completion: @escaping () -> Void) {
        DispatchQueue.main.async { [weak self] in
            guard let strongSelf = self else {
                return
            }
            let currentOrientationValue = UIDevice.current.orientation.rawValue
            let currentOrientationMask = strongSelf.convertOrientationValueToMask(currentOrientationValue)
            var nextOrientationMask: UIInterfaceOrientationMask
            var nextOrientationValue: Int
            if let orientationType {
                nextOrientationMask = strongSelf.convertOrientationTypeToMask(orientationType)
                nextOrientationValue = strongSelf.convertOrientationTypeToValue(orientationType)
            } else {
                nextOrientationMask = currentOrientationMask
                nextOrientationValue = currentOrientationValue
            }
            strongSelf.requestGeometryUpdate(orientationValue: nextOrientationValue, orientationMask: nextOrientationMask)
            ScreenOrientation.supportedInterfaceOrientations = nextOrientationMask
            UINavigationController.attemptRotationToDeviceOrientation()
            strongSelf.requestGeometryUpdate(orientationValue: currentOrientationValue, orientationMask: currentOrientationMask)
            if let orientationType {
                strongSelf.notifyOrientationChangeListeners(orientationType)
            } else {
                let convertedOrientationType = strongSelf.convertOrientationValueToType(nextOrientationValue)
                strongSelf.notifyOrientationChangeListeners(convertedOrientationType)
            }
            completion()
        }
    }

    @objc public func unlock(completion: @escaping () -> Void) {
        DispatchQueue.main.async {
            let orientationMask = UIInterfaceOrientationMask.all
            ScreenOrientation.supportedInterfaceOrientations = orientationMask
            UINavigationController.attemptRotationToDeviceOrientation()
            guard let orientationType = self.lastOrientationType else {
                return
            }
            let orientationValue = self.convertOrientationTypeToValue(orientationType)
            self.requestGeometryUpdate(orientationValue: orientationValue, orientationMask: orientationMask)
            self.notifyOrientationChangeListeners(orientationType)
            completion()
        }
    }

    @objc public func getCachedCurrentOrientationType(completion: @escaping (String) -> Void) {
        guard let cachedOrientationType = self.currentOrientationType else {
            self.getUncachedCurrentOrientationType(completion: completion)
            return
        }
        completion(cachedOrientationType)
    }

    @objc private func requestGeometryUpdate(orientationValue: Int, orientationMask: UIInterfaceOrientationMask) {
        if #available(iOS 16, *) {
            let windowScene = UIApplication.shared.connectedScenes.first as? UIWindowScene
            windowScene?.keyWindow?.rootViewController?.setNeedsUpdateOfSupportedInterfaceOrientations()
            windowScene?.requestGeometryUpdate(.iOS(interfaceOrientations: orientationMask)) { error in
                CAPLog.print("requestGeometryUpdate failed.", error)
            }
        } else {
            UIDevice.current.setValue(orientationValue, forKey: "orientation")
        }
    }

    @objc private func getUncachedCurrentOrientationType(completion: @escaping (String) -> Void) {
        DispatchQueue.main.async {
            let orientationValue = UIDevice.current.orientation.rawValue
            let orientationType = self.convertOrientationValueToType(orientationValue)
            completion(orientationType)
        }
    }

    @objc private func isCurrentOrientationValid() -> Bool {
        return UIDevice.current.orientation.isValidInterfaceOrientation
    }

    @objc private func handleOrientationChange() {
        let isValid = self.isCurrentOrientationValid()
        guard isValid else {
            return
        }
        self.getUncachedCurrentOrientationType(completion: { orientationType in
            self.lastOrientationType = orientationType
            guard ScreenOrientation.supportedInterfaceOrientations == UIInterfaceOrientationMask.all else {
                return
            }
            self.notifyOrientationChangeListeners(orientationType)
        })
    }

    @objc private func notifyOrientationChangeListeners(_ orientationType: String) {
        self.currentOrientationType = orientationType
        self.plugin.notifyOrientationChangeListeners(orientationType)
    }

    @objc private func convertOrientationValueToMask(_ orientationValue: Int) -> UIInterfaceOrientationMask {
        switch orientationValue {
        case UIInterfaceOrientation.landscapeLeft.rawValue:
            return UIInterfaceOrientationMask.landscapeLeft
        case UIInterfaceOrientation.landscapeRight.rawValue:
            return UIInterfaceOrientationMask.landscapeRight
        case UIInterfaceOrientation.portrait.rawValue:
            return UIInterfaceOrientationMask.portrait
        case UIInterfaceOrientation.portraitUpsideDown.rawValue:
            return UIInterfaceOrientationMask.portraitUpsideDown
        default:
            let isPortrait = UIApplication.shared.windows.first?.windowScene?.interfaceOrientation.isPortrait ?? false
            return isPortrait ? UIInterfaceOrientationMask.portrait : UIInterfaceOrientationMask.landscape
        }
    }

    @objc private func convertOrientationTypeToMask(_ orientationType: String) -> UIInterfaceOrientationMask {
        switch orientationType {
        case "landscape":
            return UIInterfaceOrientationMask.landscape
        case "landscape-primary":
            return UIInterfaceOrientationMask.landscapeLeft
        case "landscape-secondary":
            return UIInterfaceOrientationMask.landscapeRight
        case "portrait":
            return UIInterfaceOrientationMask.portrait
        case "portrait-primary":
            return UIInterfaceOrientationMask.portrait
        case "portrait-secondary":
            return UIInterfaceOrientationMask.portraitUpsideDown
        default:
            return UIInterfaceOrientationMask.all
        }
    }

    @objc private func convertOrientationTypeToValue(_ orientationType: String) -> Int {
        switch orientationType {
        case "landscape":
            return UIInterfaceOrientation.landscapeRight.rawValue
        case "landscape-primary":
            return UIInterfaceOrientation.landscapeLeft.rawValue
        case "landscape-secondary":
            return UIInterfaceOrientation.landscapeRight.rawValue
        case "portrait":
            return UIInterfaceOrientation.portrait.rawValue
        case "portrait-primary":
            return UIInterfaceOrientation.portrait.rawValue
        case "portrait-secondary":
            return UIInterfaceOrientation.portraitUpsideDown.rawValue
        default:
            return UIInterfaceOrientation.unknown.rawValue
        }
    }

    @objc private func convertOrientationValueToType(_ orientationValue: Int) -> String {
        switch orientationValue {
        case UIInterfaceOrientation.landscapeLeft.rawValue:
            return "landscape-primary"
        case UIInterfaceOrientation.landscapeRight.rawValue:
            return "landscape-secondary"
        case UIInterfaceOrientation.portrait.rawValue:
            return "portrait-primary"
        case UIInterfaceOrientation.portraitUpsideDown.rawValue:
            return "portrait-secondary"
        default:
            let isPortrait = UIApplication.shared.windows.first?.windowScene?.interfaceOrientation.isPortrait ?? false
            return isPortrait ? "portrait-primary" : "landscape-primary"
        }
    }
}
