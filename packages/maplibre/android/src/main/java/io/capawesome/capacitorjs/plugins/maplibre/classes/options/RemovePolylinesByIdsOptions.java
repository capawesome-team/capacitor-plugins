package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.classes.CustomExceptions;
import java.util.List;

public class RemovePolylinesByIdsOptions {

    @NonNull
    private final String mapId;

    @NonNull
    private final List<String> polylineIds;

    public RemovePolylinesByIdsOptions(@NonNull PluginCall call) throws Exception {
        JSArray polylineIdsArray = call.getArray("polylineIds", null);
        if (polylineIdsArray == null) {
            throw CustomExceptions.POLYLINE_IDS_MISSING;
        }
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
        this.polylineIds = MapLibreHelper.createStringList(polylineIdsArray);
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }

    @NonNull
    public List<String> getPolylineIds() {
        return polylineIds;
    }
}
