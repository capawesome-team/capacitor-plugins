import Capacitor
import Foundation
import UIKit

public class Padding {
    let bottom: Double
    let left: Double
    let right: Double
    let top: Double

    init(_ object: JSObject?) {
        self.bottom = MapLibreHelper.getDouble(object, "bottom") ?? 0
        self.left = MapLibreHelper.getDouble(object, "left") ?? 0
        self.right = MapLibreHelper.getDouble(object, "right") ?? 0
        self.top = MapLibreHelper.getDouble(object, "top") ?? 0
    }

    func toEdgeInsets() -> UIEdgeInsets {
        return UIEdgeInsets(top: top, left: left, bottom: bottom, right: right)
    }
}
