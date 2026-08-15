package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.classes.CustomExceptions;

public class RemoveGeoJsonSourceByIdOptions {

    @NonNull
    private final String mapId;

    @NonNull
    private final String sourceId;

    public RemoveGeoJsonSourceByIdOptions(@NonNull PluginCall call) throws Exception {
        String sourceId = call.getString("sourceId");
        if (sourceId == null) {
            throw CustomExceptions.SOURCE_ID_MISSING;
        }
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
        this.sourceId = sourceId;
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }

    @NonNull
    public String getSourceId() {
        return sourceId;
    }
}
