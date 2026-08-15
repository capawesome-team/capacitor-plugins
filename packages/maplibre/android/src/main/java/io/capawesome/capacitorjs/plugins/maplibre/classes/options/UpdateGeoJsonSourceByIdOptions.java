package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.classes.CustomExceptions;

public class UpdateGeoJsonSourceByIdOptions {

    @Nullable
    private final JSObject data;

    @NonNull
    private final String mapId;

    @NonNull
    private final String sourceId;

    @Nullable
    private final String url;

    public UpdateGeoJsonSourceByIdOptions(@NonNull PluginCall call) throws Exception {
        String sourceId = call.getString("sourceId");
        if (sourceId == null) {
            throw CustomExceptions.SOURCE_ID_MISSING;
        }
        this.data = call.getObject("data", null);
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
        this.sourceId = sourceId;
        this.url = call.getString("url");
        if ((this.data == null) == (this.url == null)) {
            throw CustomExceptions.DATA_OR_URL_MISSING;
        }
    }

    @Nullable
    public JSObject getData() {
        return data;
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }

    @NonNull
    public String getSourceId() {
        return sourceId;
    }

    @Nullable
    public String getUrl() {
        return url;
    }
}
