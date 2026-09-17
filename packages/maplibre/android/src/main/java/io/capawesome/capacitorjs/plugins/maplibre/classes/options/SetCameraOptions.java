package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.classes.Padding;
import org.maplibre.android.geometry.LatLng;

public class SetCameraOptions {

    private final boolean animate;
    private final int animationDuration;

    @Nullable
    private final Double bearing;

    @Nullable
    private final LatLng center;

    @NonNull
    private final String mapId;

    @Nullable
    private final Padding padding;

    @Nullable
    private final Double pitch;

    @Nullable
    private final Double zoom;

    public SetCameraOptions(@NonNull PluginCall call) throws Exception {
        JSObject centerObject = call.getObject("center", null);
        JSObject paddingObject = call.getObject("padding", null);
        this.animate = Boolean.TRUE.equals(call.getBoolean("animate", false));
        this.animationDuration = call.getInt("animationDuration", 300);
        this.bearing = call.getDouble("bearing");
        this.center = centerObject == null ? null : MapLibreHelper.createLatLng(centerObject);
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
        this.padding = paddingObject == null ? null : new Padding(paddingObject);
        this.pitch = call.getDouble("pitch");
        this.zoom = call.getDouble("zoom");
    }

    public boolean getAnimate() {
        return animate;
    }

    public int getAnimationDuration() {
        return animationDuration;
    }

    @Nullable
    public Double getBearing() {
        return bearing;
    }

    @Nullable
    public LatLng getCenter() {
        return center;
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }

    @Nullable
    public Padding getPadding() {
        return padding;
    }

    @Nullable
    public Double getPitch() {
        return pitch;
    }

    @Nullable
    public Double getZoom() {
        return zoom;
    }
}
