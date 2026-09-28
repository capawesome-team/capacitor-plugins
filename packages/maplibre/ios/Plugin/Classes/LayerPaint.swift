import Capacitor
import Foundation

public class LayerPaint {
    let circleBlur: NSExpression?
    let circleColor: NSExpression?
    let circleOpacity: NSExpression?
    let circleRadius: NSExpression?
    let circleStrokeColor: NSExpression?
    let circleStrokeWidth: NSExpression?
    let fillColor: NSExpression?
    let fillOpacity: NSExpression?
    let fillOutlineColor: NSExpression?
    let heatmapColor: NSExpression?
    let heatmapIntensity: NSExpression?
    let heatmapOpacity: NSExpression?
    let heatmapRadius: NSExpression?
    let heatmapWeight: NSExpression?
    let lineColor: NSExpression?
    let lineOpacity: NSExpression?
    let lineWidth: NSExpression?

    init(_ object: JSObject?) throws {
        self.circleBlur = try MapLibreHelper.createExpression(object?["circleBlur"])
        self.circleColor = try MapLibreHelper.createExpression(object?["circleColor"])
        self.circleOpacity = try MapLibreHelper.createExpression(object?["circleOpacity"])
        self.circleRadius = try MapLibreHelper.createExpression(object?["circleRadius"])
        self.circleStrokeColor = try MapLibreHelper.createExpression(object?["circleStrokeColor"])
        self.circleStrokeWidth = try MapLibreHelper.createExpression(object?["circleStrokeWidth"])
        self.fillColor = try MapLibreHelper.createExpression(object?["fillColor"])
        self.fillOpacity = try MapLibreHelper.createExpression(object?["fillOpacity"])
        self.fillOutlineColor = try MapLibreHelper.createExpression(object?["fillOutlineColor"])
        self.heatmapColor = try MapLibreHelper.createExpression(object?["heatmapColor"])
        self.heatmapIntensity = try MapLibreHelper.createExpression(object?["heatmapIntensity"])
        self.heatmapOpacity = try MapLibreHelper.createExpression(object?["heatmapOpacity"])
        self.heatmapRadius = try MapLibreHelper.createExpression(object?["heatmapRadius"])
        self.heatmapWeight = try MapLibreHelper.createExpression(object?["heatmapWeight"])
        self.lineColor = try MapLibreHelper.createExpression(object?["lineColor"])
        self.lineOpacity = try MapLibreHelper.createExpression(object?["lineOpacity"])
        self.lineWidth = try MapLibreHelper.createExpression(object?["lineWidth"])
    }
}
