package io.capawesome.capacitorjs.plugins.maplibre.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.maplibre.classes.Camera;
import io.capawesome.capacitorjs.plugins.maplibre.interfaces.Result;

public class GetCameraResult implements Result {

    @NonNull
    private final Camera camera;

    public GetCameraResult(@NonNull Camera camera) {
        this.camera = camera;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("camera", camera.toJSObject());
        return result;
    }
}
