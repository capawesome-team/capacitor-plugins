package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.classes.CustomExceptions;
import java.util.List;

public class RemoveMarkersByIdsOptions {

    @NonNull
    private final String mapId;

    @NonNull
    private final List<String> markerIds;

    public RemoveMarkersByIdsOptions(@NonNull PluginCall call) throws Exception {
        JSArray markerIdsArray = call.getArray("markerIds", null);
        if (markerIdsArray == null) {
            throw CustomExceptions.MARKER_IDS_MISSING;
        }
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
        this.markerIds = MapLibreHelper.createStringList(markerIdsArray);
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }

    @NonNull
    public List<String> getMarkerIds() {
        return markerIds;
    }
}
