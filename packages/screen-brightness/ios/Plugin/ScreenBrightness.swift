import Foundation
import UIKit

@objc public class ScreenBrightness: NSObject {
    private let plugin: ScreenBrightnessPlugin

    init(plugin: ScreenBrightnessPlugin) {
        self.plugin = plugin
    }

    @objc public func getBrightness(completion: @escaping (GetBrightnessResult?, Error?) -> Void) {
        DispatchQueue.main.async {
            let brightness = Double(UIScreen.main.brightness)
            let result = GetBrightnessResult(brightness: brightness)
            completion(result, nil)
        }
    }

    @objc public func setBrightness(_ options: SetBrightnessOptions, completion: @escaping (Error?) -> Void) {
        let brightness = options.brightness
        DispatchQueue.main.async {
            UIScreen.main.brightness = CGFloat(brightness)
            completion(nil)
        }
    }
}
