package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.classes.GestureSettings;

public class SetGesturesEnabledOptions {

    @NonNull
    private final GestureSettings gestures;

    @NonNull
    private final String mapId;

    public SetGesturesEnabledOptions(@NonNull PluginCall call) throws Exception {
        this.gestures = new GestureSettings(call.getData());
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
    }

    @NonNull
    public GestureSettings getGestures() {
        return gestures;
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }
}
