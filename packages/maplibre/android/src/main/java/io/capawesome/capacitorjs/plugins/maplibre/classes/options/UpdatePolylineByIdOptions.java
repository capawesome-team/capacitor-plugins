package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.classes.CustomExceptions;
import java.util.List;
import org.maplibre.android.geometry.LatLng;

public class UpdatePolylineByIdOptions {

    @Nullable
    private final Integer color;

    @Nullable
    private final List<LatLng> coordinates;

    @NonNull
    private final String mapId;

    @Nullable
    private final Double opacity;

    @NonNull
    private final String polylineId;

    @Nullable
    private final Double width;

    public UpdatePolylineByIdOptions(@NonNull PluginCall call) throws Exception {
        JSArray coordinatesArray = call.getArray("coordinates", null);
        String color = call.getString("color");
        String polylineId = call.getString("polylineId");
        if (polylineId == null) {
            throw CustomExceptions.POLYLINE_ID_MISSING;
        }
        this.color = color == null ? null : MapLibreHelper.parseColor(color);
        this.coordinates = coordinatesArray == null ? null : MapLibreHelper.createLatLngList(coordinatesArray);
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
        this.opacity = call.getDouble("opacity");
        this.polylineId = polylineId;
        this.width = call.getDouble("width");
    }

    @Nullable
    public Integer getColor() {
        return color;
    }

    @Nullable
    public List<LatLng> getCoordinates() {
        return coordinates;
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }

    @Nullable
    public Double getOpacity() {
        return opacity;
    }

    @NonNull
    public String getPolylineId() {
        return polylineId;
    }

    @Nullable
    public Double getWidth() {
        return width;
    }
}
