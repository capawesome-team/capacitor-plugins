package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.LayerPaint;
import java.util.Arrays;
import java.util.List;

public class AddLayerOptions {

    private static final List<String> LAYER_TYPES = Arrays.asList("circle", "fill", "line");

    @Nullable
    private final String belowLayerId;

    @NonNull
    private final String layerId;

    @NonNull
    private final String mapId;

    @Nullable
    private final Double maxZoom;

    @Nullable
    private final Double minZoom;

    @NonNull
    private final LayerPaint paint;

    @NonNull
    private final String sourceId;

    @NonNull
    private final String type;

    public AddLayerOptions(@NonNull PluginCall call) throws Exception {
        String layerId = call.getString("layerId");
        String sourceId = call.getString("sourceId");
        String type = call.getString("type");
        if (layerId == null) {
            throw CustomExceptions.LAYER_ID_MISSING;
        }
        if (sourceId == null) {
            throw CustomExceptions.SOURCE_ID_MISSING;
        }
        if (type == null || !LAYER_TYPES.contains(type)) {
            throw CustomExceptions.LAYER_TYPE_INVALID;
        }
        this.belowLayerId = call.getString("belowLayerId");
        this.layerId = layerId;
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
        this.maxZoom = call.getDouble("maxZoom");
        this.minZoom = call.getDouble("minZoom");
        this.paint = new LayerPaint(call.getObject("paint", new JSObject()));
        this.sourceId = sourceId;
        this.type = type;
    }

    @Nullable
    public String getBelowLayerId() {
        return belowLayerId;
    }

    @NonNull
    public String getLayerId() {
        return layerId;
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }

    @Nullable
    public Double getMaxZoom() {
        return maxZoom;
    }

    @Nullable
    public Double getMinZoom() {
        return minZoom;
    }

    @NonNull
    public LayerPaint getPaint() {
        return paint;
    }

    @NonNull
    public String getSourceId() {
        return sourceId;
    }

    @NonNull
    public String getType() {
        return type;
    }
}
