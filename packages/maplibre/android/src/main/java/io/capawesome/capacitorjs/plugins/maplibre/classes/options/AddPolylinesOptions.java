package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.Polyline;
import java.util.ArrayList;
import java.util.List;

public class AddPolylinesOptions {

    @NonNull
    private final String mapId;

    @NonNull
    private final List<Polyline> polylines = new ArrayList<>();

    public AddPolylinesOptions(@NonNull PluginCall call) throws Exception {
        JSArray polylinesArray = call.getArray("polylines", null);
        if (polylinesArray == null) {
            throw CustomExceptions.POLYLINES_MISSING;
        }
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
        for (JSObject polylineObject : MapLibreHelper.createObjectList(polylinesArray)) {
            this.polylines.add(new Polyline(polylineObject));
        }
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }

    @NonNull
    public List<Polyline> getPolylines() {
        return polylines;
    }
}
