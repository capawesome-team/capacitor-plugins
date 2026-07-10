package io.capawesome.capacitorjs.plugins.textzoom.classes.options;

import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.textzoom.classes.CustomExceptions;

public class SetZoomOptions {

    private final double zoom;

    public SetZoomOptions(PluginCall call) throws Exception {
        this.zoom = SetZoomOptions.getZoomFromCall(call);
    }

    public double getZoom() {
        return zoom;
    }

    private static double getZoomFromCall(PluginCall call) throws Exception {
        Double zoom = call.getDouble("zoom");
        if (zoom == null) {
            throw CustomExceptions.ZOOM_MISSING;
        }
        if (zoom <= 0.0) {
            throw CustomExceptions.ZOOM_INVALID;
        }
        return zoom;
    }
}
