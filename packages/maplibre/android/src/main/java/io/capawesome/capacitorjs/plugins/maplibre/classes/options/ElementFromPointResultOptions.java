package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.classes.CustomExceptions;

public class ElementFromPointResultOptions {

    @Nullable
    private final String mapId;

    @NonNull
    private final String requestId;

    public ElementFromPointResultOptions(@NonNull PluginCall call) throws Exception {
        String requestId = call.getString("requestId");
        if (requestId == null) {
            throw CustomExceptions.REQUEST_ID_MISSING;
        }
        this.mapId = call.getString("mapId");
        this.requestId = requestId;
    }

    @Nullable
    public String getMapId() {
        return mapId;
    }

    @NonNull
    public String getRequestId() {
        return requestId;
    }
}
