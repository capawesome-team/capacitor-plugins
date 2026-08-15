package io.capawesome.capacitorjs.plugins.maplibre.classes;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;

/**
 * The position and size of a map in CSS pixels, relative to the viewport.
 */
public class MapFrame {

    private final double height;
    private final double width;
    private final double x;
    private final double y;

    public MapFrame(@NonNull JSObject object) throws Exception {
        Double height = MapLibreHelper.getDouble(object, "height");
        Double width = MapLibreHelper.getDouble(object, "width");
        Double x = MapLibreHelper.getDouble(object, "x");
        Double y = MapLibreHelper.getDouble(object, "y");
        if (height == null || width == null || x == null || y == null) {
            throw CustomExceptions.FRAME_MISSING;
        }
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
    }

    @NonNull
    public static MapFrame getFrameFromCall(@NonNull PluginCall call) throws Exception {
        JSObject object = call.getObject("frame", null);
        if (object == null) {
            throw CustomExceptions.FRAME_MISSING;
        }
        return new MapFrame(object);
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
}
