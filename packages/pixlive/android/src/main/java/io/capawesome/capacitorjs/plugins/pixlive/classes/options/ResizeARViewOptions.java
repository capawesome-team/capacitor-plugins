package io.capawesome.capacitorjs.plugins.pixlive.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.pixlive.classes.CustomExceptions;

public class ResizeARViewOptions {

    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public ResizeARViewOptions(@NonNull PluginCall call) throws Exception {
        this.x = ResizeARViewOptions.getXFromCall(call);
        this.y = ResizeARViewOptions.getYFromCall(call);
        this.width = ResizeARViewOptions.getWidthFromCall(call);
        this.height = ResizeARViewOptions.getHeightFromCall(call);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private static int getXFromCall(@NonNull PluginCall call) throws Exception {
        Integer x = call.getInt("x");
        if (x == null) {
            throw CustomExceptions.X_MISSING;
        }
        return x;
    }

    private static int getYFromCall(@NonNull PluginCall call) throws Exception {
        Integer y = call.getInt("y");
        if (y == null) {
            throw CustomExceptions.Y_MISSING;
        }
        return y;
    }

    private static int getWidthFromCall(@NonNull PluginCall call) throws Exception {
        Integer width = call.getInt("width");
        if (width == null) {
            throw CustomExceptions.WIDTH_MISSING;
        }
        return width;
    }

    private static int getHeightFromCall(@NonNull PluginCall call) throws Exception {
        Integer height = call.getInt("height");
        if (height == null) {
            throw CustomExceptions.HEIGHT_MISSING;
        }
        return height;
    }
}
