package io.capawesome.capacitorjs.plugins.screenorientation;

import android.content.pm.ActivityInfo;
import androidx.annotation.NonNull;

public enum ScreenOrientationType {
    LANDSCAPE("landscape", ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE),
    LANDSCAPE_PRIMARY("landscape-primary", ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE),
    LANDSCAPE_SECONDARY("landscape-secondary", ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE),
    PORTRAIT("portrait", ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT),
    PORTRAIT_PRIMARY("portrait-primary", ActivityInfo.SCREEN_ORIENTATION_PORTRAIT),
    PORTRAIT_SECONDARY("portrait-secondary", ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);

    private final String key;
    private final int orientation;

    ScreenOrientationType(String key, int orientation) {
        this.key = key;
        this.orientation = orientation;
    }

    public String key() {
        return key;
    }

    public int orientation() {
        return orientation;
    }

    @NonNull
    public static ScreenOrientationType fromString(String s) throws Exception {
        String k = s.toLowerCase();
        for (ScreenOrientationType t : values()) {
            if (t.key.equals(k)) return t;
        }
        throw new Exception(ScreenOrientationPlugin.ERROR_UNSUPPORTED_ORIENTATION_TYPE);
    }

    public static int toInt(@NonNull String type) throws Exception {
        return fromString(type).orientation();
    }
}
