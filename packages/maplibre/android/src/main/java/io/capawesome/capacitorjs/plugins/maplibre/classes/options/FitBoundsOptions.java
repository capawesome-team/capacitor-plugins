package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.Padding;
import org.maplibre.android.geometry.LatLngBounds;

public class FitBoundsOptions {

    private final boolean animate;
    private final int animationDuration;

    @NonNull
    private final LatLngBounds bounds;

    @NonNull
    private final String mapId;

    @Nullable
    private final Double maxZoom;

    @NonNull
    private final Padding padding;

    public FitBoundsOptions(@NonNull PluginCall call) throws Exception {
        JSObject boundsObject = call.getObject("bounds", null);
        if (boundsObject == null) {
            throw CustomExceptions.BOUNDS_MISSING;
        }
        this.animate = Boolean.TRUE.equals(call.getBoolean("animate", false));
        this.animationDuration = call.getInt("animationDuration", 300);
        this.bounds = MapLibreHelper.createLatLngBounds(boundsObject);
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
        this.maxZoom = call.getDouble("maxZoom");
        this.padding = new Padding(call.getObject("padding", null));
    }

    public boolean getAnimate() {
        return animate;
    }

    public int getAnimationDuration() {
        return animationDuration;
    }

    @NonNull
    public LatLngBounds getBounds() {
        return bounds;
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }

    @Nullable
    public Double getMaxZoom() {
        return maxZoom;
    }

    @NonNull
    public Padding getPadding() {
        return padding;
    }
}
