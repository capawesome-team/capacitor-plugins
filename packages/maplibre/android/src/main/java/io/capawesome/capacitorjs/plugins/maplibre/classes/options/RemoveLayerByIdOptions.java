package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.classes.CustomExceptions;

public class RemoveLayerByIdOptions {

    @NonNull
    private final String layerId;

    @NonNull
    private final String mapId;

    public RemoveLayerByIdOptions(@NonNull PluginCall call) throws Exception {
        String layerId = call.getString("layerId");
        if (layerId == null) {
            throw CustomExceptions.LAYER_ID_MISSING;
        }
        this.layerId = layerId;
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
    }

    @NonNull
    public String getLayerId() {
        return layerId;
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }
}
