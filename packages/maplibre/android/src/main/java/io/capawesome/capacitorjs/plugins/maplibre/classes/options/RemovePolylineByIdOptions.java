package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.classes.CustomExceptions;

public class RemovePolylineByIdOptions {

    @NonNull
    private final String mapId;

    @NonNull
    private final String polylineId;

    public RemovePolylineByIdOptions(@NonNull PluginCall call) throws Exception {
        String polylineId = call.getString("polylineId");
        if (polylineId == null) {
            throw CustomExceptions.POLYLINE_ID_MISSING;
        }
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
        this.polylineId = polylineId;
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }

    @NonNull
    public String getPolylineId() {
        return polylineId;
    }
}
