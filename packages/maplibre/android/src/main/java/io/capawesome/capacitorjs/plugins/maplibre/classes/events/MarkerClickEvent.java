package io.capawesome.capacitorjs.plugins.maplibre.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.interfaces.Result;
import org.maplibre.android.geometry.LatLng;

public class MarkerClickEvent implements Result {

    @NonNull
    private final LatLng coordinates;

    @NonNull
    private final String mapId;

    @NonNull
    private final String markerId;

    private final double x;
    private final double y;

    public MarkerClickEvent(@NonNull LatLng coordinates, @NonNull String mapId, @NonNull String markerId, double x, double y) {
        this.coordinates = coordinates;
        this.mapId = mapId;
        this.markerId = markerId;
        this.x = x;
        this.y = y;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject point = new JSObject();
        point.put("x", x);
        point.put("y", y);
        JSObject result = new JSObject();
        result.put("coordinates", MapLibreHelper.createLatLngObject(coordinates));
        result.put("mapId", mapId);
        result.put("markerId", markerId);
        result.put("point", point);
        return result;
    }
}
