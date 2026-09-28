package io.capawesome.capacitorjs.plugins.maplibre.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import org.maplibre.android.style.expressions.Expression;

/**
 * The paint properties of a layer. Properties that do not apply to the type of the layer are ignored.
 */
public class LayerPaint {

    @Nullable
    private final Expression circleBlur;

    @Nullable
    private final Expression circleColor;

    @Nullable
    private final Expression circleOpacity;

    @Nullable
    private final Expression circleRadius;

    @Nullable
    private final Expression circleStrokeColor;

    @Nullable
    private final Expression circleStrokeWidth;

    @Nullable
    private final Expression fillColor;

    @Nullable
    private final Expression fillOpacity;

    @Nullable
    private final Expression fillOutlineColor;

    @Nullable
    private final Expression heatmapColor;

    @Nullable
    private final Expression heatmapIntensity;

    @Nullable
    private final Expression heatmapOpacity;

    @Nullable
    private final Expression heatmapRadius;

    @Nullable
    private final Expression heatmapWeight;

    @Nullable
    private final Expression lineColor;

    @Nullable
    private final Expression lineOpacity;

    @Nullable
    private final Expression lineWidth;

    public LayerPaint(@NonNull JSObject object) throws Exception {
        this.circleBlur = MapLibreHelper.createExpression(object.opt("circleBlur"));
        this.circleColor = MapLibreHelper.createExpression(object.opt("circleColor"));
        this.circleOpacity = MapLibreHelper.createExpression(object.opt("circleOpacity"));
        this.circleRadius = MapLibreHelper.createExpression(object.opt("circleRadius"));
        this.circleStrokeColor = MapLibreHelper.createExpression(object.opt("circleStrokeColor"));
        this.circleStrokeWidth = MapLibreHelper.createExpression(object.opt("circleStrokeWidth"));
        this.fillColor = MapLibreHelper.createExpression(object.opt("fillColor"));
        this.fillOpacity = MapLibreHelper.createExpression(object.opt("fillOpacity"));
        this.fillOutlineColor = MapLibreHelper.createExpression(object.opt("fillOutlineColor"));
        this.heatmapColor = MapLibreHelper.createExpression(object.opt("heatmapColor"));
        this.heatmapIntensity = MapLibreHelper.createExpression(object.opt("heatmapIntensity"));
        this.heatmapOpacity = MapLibreHelper.createExpression(object.opt("heatmapOpacity"));
        this.heatmapRadius = MapLibreHelper.createExpression(object.opt("heatmapRadius"));
        this.heatmapWeight = MapLibreHelper.createExpression(object.opt("heatmapWeight"));
        this.lineColor = MapLibreHelper.createExpression(object.opt("lineColor"));
        this.lineOpacity = MapLibreHelper.createExpression(object.opt("lineOpacity"));
        this.lineWidth = MapLibreHelper.createExpression(object.opt("lineWidth"));
    }

    @Nullable
    public Expression getCircleBlur() {
        return circleBlur;
    }

    @Nullable
    public Expression getCircleColor() {
        return circleColor;
    }

    @Nullable
    public Expression getCircleOpacity() {
        return circleOpacity;
    }

    @Nullable
    public Expression getCircleRadius() {
        return circleRadius;
    }

    @Nullable
    public Expression getCircleStrokeColor() {
        return circleStrokeColor;
    }

    @Nullable
    public Expression getCircleStrokeWidth() {
        return circleStrokeWidth;
    }

    @Nullable
    public Expression getFillColor() {
        return fillColor;
    }

    @Nullable
    public Expression getFillOpacity() {
        return fillOpacity;
    }

    @Nullable
    public Expression getFillOutlineColor() {
        return fillOutlineColor;
    }

    @Nullable
    public Expression getHeatmapColor() {
        return heatmapColor;
    }

    @Nullable
    public Expression getHeatmapIntensity() {
        return heatmapIntensity;
    }

    @Nullable
    public Expression getHeatmapOpacity() {
        return heatmapOpacity;
    }

    @Nullable
    public Expression getHeatmapRadius() {
        return heatmapRadius;
    }

    @Nullable
    public Expression getHeatmapWeight() {
        return heatmapWeight;
    }

    @Nullable
    public Expression getLineColor() {
        return lineColor;
    }

    @Nullable
    public Expression getLineOpacity() {
        return lineOpacity;
    }

    @Nullable
    public Expression getLineWidth() {
        return lineWidth;
    }
}
