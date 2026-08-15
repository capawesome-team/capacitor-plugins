package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.classes.CustomExceptions;

public class RemoveMarkerByIdOptions {

    @NonNull
    private final String mapId;

    @NonNull
    private final String markerId;

    public RemoveMarkerByIdOptions(@NonNull PluginCall call) throws Exception {
        String markerId = call.getString("markerId");
        if (markerId == null) {
            throw CustomExceptions.MARKER_ID_MISSING;
        }
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
        this.markerId = markerId;
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }

    @NonNull
    public String getMarkerId() {
        return markerId;
    }
}
