package io.capawesome.capacitorjs.plugins.maplibre.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;

/**
 * The gestures the user can perform on a map. A gesture that is not provided is left unchanged.
 */
public class GestureSettings {

    @Nullable
    private final Boolean pan;

    @Nullable
    private final Boolean rotate;

    @Nullable
    private final Boolean tilt;

    @Nullable
    private final Boolean zoom;

    public GestureSettings(@NonNull JSObject object) {
        this.pan = object.getBoolean("pan", null);
        this.rotate = object.getBoolean("rotate", null);
        this.tilt = object.getBoolean("tilt", null);
        this.zoom = object.getBoolean("zoom", null);
    }

    @Nullable
    public Boolean getPan() {
        return pan;
    }

    @Nullable
    public Boolean getRotate() {
        return rotate;
    }

    @Nullable
    public Boolean getTilt() {
        return tilt;
    }

    @Nullable
    public Boolean getZoom() {
        return zoom;
    }
}
