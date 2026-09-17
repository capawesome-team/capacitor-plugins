package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.Marker;
import java.util.ArrayList;
import java.util.List;

public class AddMarkersOptions {

    @NonNull
    private final String mapId;

    @NonNull
    private final List<Marker> markers = new ArrayList<>();

    public AddMarkersOptions(@NonNull PluginCall call) throws Exception {
        JSArray markersArray = call.getArray("markers", null);
        if (markersArray == null) {
            throw CustomExceptions.MARKERS_MISSING;
        }
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
        for (JSObject markerObject : MapLibreHelper.createObjectList(markersArray)) {
            this.markers.add(new Marker(markerObject));
        }
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }

    @NonNull
    public List<Marker> getMarkers() {
        return markers;
    }
}
