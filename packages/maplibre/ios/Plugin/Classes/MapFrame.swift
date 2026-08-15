import Capacitor
import CoreGraphics
import Foundation

public class MapFrame {
    let height: Double
    let width: Double

    private init(height: Double, width: Double) {
        self.height = height
        self.width = width
    }

    // The position of the frame is not used on iOS because the native map view is
    // positioned by the web view, not by the plugin.
    static func fromJSObject(_ object: JSObject?) -> MapFrame? {
        guard let height = MapLibreHelper.getDouble(object, "height"),
              let width = MapLibreHelper.getDouble(object, "width") else {
            return nil
        }
        return MapFrame(height: height, width: width)
    }

    func toCGRect() -> CGRect {
        return CGRect(x: 0, y: 0, width: width, height: height)
    }
}
