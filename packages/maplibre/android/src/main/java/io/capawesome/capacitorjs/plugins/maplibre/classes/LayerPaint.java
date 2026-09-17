package io.capawesome.capacitorjs.plugins.maplibre.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;

/**
 * The paint properties of a layer. Properties that do not apply to the type of the layer are ignored.
 */
public class LayerPaint {

    @Nullable
    private final Integer circleColor;

    @Nullable
    private final Double circleOpacity;

    @Nullable
    private final Double circleRadius;

    @Nullable
    private final Integer circleStrokeColor;

    @Nullable
    private final Double circleStrokeWidth;

    @Nullable
    private final Integer fillColor;

    @Nullable
    private final Double fillOpacity;

    @Nullable
    private final Integer fillOutlineColor;

    @Nullable
    private final Integer lineColor;

    @Nullable
    private final Double lineOpacity;

    @Nullable
    private final Double lineWidth;

    public LayerPaint(@NonNull JSObject object) throws Exception {
        this.circleColor = LayerPaint.getColor(object, "circleColor");
        this.circleOpacity = MapLibreHelper.getDouble(object, "circleOpacity");
        this.circleRadius = MapLibreHelper.getDouble(object, "circleRadius");
        this.circleStrokeColor = LayerPaint.getColor(object, "circleStrokeColor");
        this.circleStrokeWidth = MapLibreHelper.getDouble(object, "circleStrokeWidth");
        this.fillColor = LayerPaint.getColor(object, "fillColor");
        this.fillOpacity = MapLibreHelper.getDouble(object, "fillOpacity");
        this.fillOutlineColor = LayerPaint.getColor(object, "fillOutlineColor");
        this.lineColor = LayerPaint.getColor(object, "lineColor");
        this.lineOpacity = MapLibreHelper.getDouble(object, "lineOpacity");
        this.lineWidth = MapLibreHelper.getDouble(object, "lineWidth");
    }

    @Nullable
    public Integer getCircleColor() {
        return circleColor;
    }

    @Nullable
    public Double getCircleOpacity() {
        return circleOpacity;
    }

    @Nullable
    public Double getCircleRadius() {
        return circleRadius;
    }

    @Nullable
    public Integer getCircleStrokeColor() {
        return circleStrokeColor;
    }

    @Nullable
    public Double getCircleStrokeWidth() {
        return circleStrokeWidth;
    }

    @Nullable
    public Integer getFillColor() {
        return fillColor;
    }

    @Nullable
    public Double getFillOpacity() {
        return fillOpacity;
    }

    @Nullable
    public Integer getFillOutlineColor() {
        return fillOutlineColor;
    }

    @Nullable
    public Integer getLineColor() {
        return lineColor;
    }

    @Nullable
    public Double getLineOpacity() {
        return lineOpacity;
    }

    @Nullable
    public Double getLineWidth() {
        return lineWidth;
    }

    @Nullable
    private static Integer getColor(@NonNull JSObject object, @NonNull String key) throws Exception {
        String color = object.getString(key, null);
        return color == null ? null : MapLibreHelper.parseColor(color);
    }
}
