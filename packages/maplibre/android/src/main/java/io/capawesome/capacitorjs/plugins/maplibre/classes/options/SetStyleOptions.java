package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.classes.CustomExceptions;

public class SetStyleOptions {

    @Nullable
    private final String json;

    @NonNull
    private final String mapId;

    @Nullable
    private final String url;

    public SetStyleOptions(@NonNull PluginCall call) throws Exception {
        this.json = call.getString("json");
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
        this.url = call.getString("url");
        if ((this.json == null) == (this.url == null)) {
            throw CustomExceptions.JSON_OR_URL_MISSING;
        }
    }

    @Nullable
    public String getJson() {
        return json;
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }

    @Nullable
    public String getUrl() {
        return url;
    }
}
