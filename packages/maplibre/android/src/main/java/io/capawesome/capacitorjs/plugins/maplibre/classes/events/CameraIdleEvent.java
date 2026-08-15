package io.capawesome.capacitorjs.plugins.maplibre.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.maplibre.classes.Camera;
import io.capawesome.capacitorjs.plugins.maplibre.interfaces.Result;

public class CameraIdleEvent implements Result {

    @NonNull
    private final Camera camera;

    @NonNull
    private final String mapId;

    public CameraIdleEvent(@NonNull Camera camera, @NonNull String mapId) {
        this.camera = camera;
        this.mapId = mapId;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("camera", camera.toJSObject());
        result.put("mapId", mapId);
        return result;
    }
}
