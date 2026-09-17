package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;

public class EnableUserLocationOptions {

    @NonNull
    private final String mapId;

    @NonNull
    private final String trackingMode;

    public EnableUserLocationOptions(@NonNull PluginCall call) throws Exception {
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
        this.trackingMode = call.getString("trackingMode", "none");
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }

    @NonNull
    public String getTrackingMode() {
        return trackingMode;
    }
}
