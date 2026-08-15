package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.Marker;

public class AddMarkerOptions {

    @NonNull
    private final String mapId;

    @NonNull
    private final Marker marker;

    public AddMarkerOptions(@NonNull PluginCall call) throws Exception {
        JSObject markerObject = call.getObject("marker", null);
        if (markerObject == null) {
            throw CustomExceptions.MARKER_MISSING;
        }
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
        this.marker = new Marker(markerObject);
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }

    @NonNull
    public Marker getMarker() {
        return marker;
    }
}
