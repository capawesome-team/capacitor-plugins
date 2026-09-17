package io.capawesome.capacitorjs.plugins.youtubeplayer.classes;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;

public class PlayerFrame {

    public static final double MINIMUM_SIZE = 200;

    private final double height;
    private final double width;
    private final double x;
    private final double y;

    public PlayerFrame(@NonNull JSObject frameObject) throws Exception {
        this.height = PlayerFrame.getValueFromObject(frameObject, "height");
        this.width = PlayerFrame.getValueFromObject(frameObject, "width");
        this.x = PlayerFrame.getValueFromObject(frameObject, "x");
        this.y = PlayerFrame.getValueFromObject(frameObject, "y");
        if (this.width < MINIMUM_SIZE || this.height < MINIMUM_SIZE) {
            throw CustomExceptions.FRAME_TOO_SMALL;
        }
    }

    @NonNull
    public static PlayerFrame getFrameFromCall(@NonNull PluginCall call) throws Exception {
        JSObject frameObject = call.getObject("frame", null);
        if (frameObject == null) {
            throw CustomExceptions.FRAME_MISSING;
        }
        return new PlayerFrame(frameObject);
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    private static double getValueFromObject(@NonNull JSObject object, @NonNull String key) throws Exception {
        if (!object.has(key)) {
            throw CustomExceptions.FRAME_INVALID;
        }
        try {
            return object.getDouble(key);
        } catch (Exception exception) {
            throw CustomExceptions.FRAME_INVALID;
        }
    }
}
