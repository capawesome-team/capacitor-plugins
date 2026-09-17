import Capacitor
import Foundation
import UIKit

public class LayerPaint {
    let circleColor: UIColor?
    let circleOpacity: Double?
    let circleRadius: Double?
    let circleStrokeColor: UIColor?
    let circleStrokeWidth: Double?
    let fillColor: UIColor?
    let fillOpacity: Double?
    let fillOutlineColor: UIColor?
    let lineColor: UIColor?
    let lineOpacity: Double?
    let lineWidth: Double?

    init(_ object: JSObject?) {
        self.circleColor = MapLibreHelper.getColor(object?["circleColor"] as? String)
        self.circleOpacity = MapLibreHelper.getDouble(object, "circleOpacity")
        self.circleRadius = MapLibreHelper.getDouble(object, "circleRadius")
        self.circleStrokeColor = MapLibreHelper.getColor(object?["circleStrokeColor"] as? String)
        self.circleStrokeWidth = MapLibreHelper.getDouble(object, "circleStrokeWidth")
        self.fillColor = MapLibreHelper.getColor(object?["fillColor"] as? String)
        self.fillOpacity = MapLibreHelper.getDouble(object, "fillOpacity")
        self.fillOutlineColor = MapLibreHelper.getColor(object?["fillOutlineColor"] as? String)
        self.lineColor = MapLibreHelper.getColor(object?["lineColor"] as? String)
        self.lineOpacity = MapLibreHelper.getDouble(object, "lineOpacity")
        self.lineWidth = MapLibreHelper.getDouble(object, "lineWidth")
    }
}
