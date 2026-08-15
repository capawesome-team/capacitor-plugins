package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.Polyline;

public class AddPolylineOptions {

    @NonNull
    private final String mapId;

    @NonNull
    private final Polyline polyline;

    public AddPolylineOptions(@NonNull PluginCall call) throws Exception {
        JSObject polylineObject = call.getObject("polyline", null);
        if (polylineObject == null) {
            throw CustomExceptions.POLYLINE_MISSING;
        }
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
        this.polyline = new Polyline(polylineObject);
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }

    @NonNull
    public Polyline getPolyline() {
        return polyline;
    }
}
