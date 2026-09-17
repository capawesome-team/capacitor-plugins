import Capacitor
import CoreGraphics
import Foundation

public class MapContentSize {
    let height: Double
    let width: Double

    private init(height: Double, width: Double) {
        self.height = height
        self.width = width
    }

    static func fromJSObject(_ object: JSObject?) -> MapContentSize? {
        guard let height = MapLibreHelper.getDouble(object, "height"),
              let width = MapLibreHelper.getDouble(object, "width") else {
            return nil
        }
        return MapContentSize(height: height, width: width)
    }

    func matches(_ size: CGSize) -> Bool {
        let tolerance = 2.0
        return abs(Double(size.height) - height) < tolerance && abs(Double(size.width) - width) < tolerance
    }
}
