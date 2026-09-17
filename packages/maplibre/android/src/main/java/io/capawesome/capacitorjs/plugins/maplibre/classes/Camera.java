package io.capawesome.capacitorjs.plugins.maplibre.classes;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.interfaces.Result;
import org.maplibre.android.camera.CameraPosition;
import org.maplibre.android.geometry.LatLng;

public class Camera implements Result {

    private final double bearing;

    @NonNull
    private final LatLng center;

    private final double pitch;
    private final double zoom;

    public Camera(@NonNull CameraPosition position) {
        LatLng target = position.target;
        this.bearing = position.bearing;
        this.center = target == null ? new LatLng(0, 0) : target;
        this.pitch = position.tilt;
        this.zoom = position.zoom;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("bearing", bearing);
        result.put("center", MapLibreHelper.createLatLngObject(center));
        result.put("pitch", pitch);
        result.put("zoom", zoom);
        return result;
    }
}
