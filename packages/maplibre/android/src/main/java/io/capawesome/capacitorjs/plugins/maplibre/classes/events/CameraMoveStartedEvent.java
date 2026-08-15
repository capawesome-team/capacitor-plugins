package io.capawesome.capacitorjs.plugins.maplibre.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.maplibre.interfaces.Result;

public class CameraMoveStartedEvent implements Result {

    @NonNull
    private final String mapId;

    @NonNull
    private final String reason;

    public CameraMoveStartedEvent(@NonNull String mapId, @NonNull String reason) {
        this.mapId = mapId;
        this.reason = reason;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("mapId", mapId);
        result.put("reason", reason);
        return result;
    }
}
