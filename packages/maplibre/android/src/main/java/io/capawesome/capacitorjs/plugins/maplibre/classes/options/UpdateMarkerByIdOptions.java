package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.maplibre.classes.MarkerIcon;
import org.maplibre.android.geometry.LatLng;

public class UpdateMarkerByIdOptions {

    private final boolean animate;
    private final int animationDuration;

    @Nullable
    private final LatLng coordinates;

    @Nullable
    private final Boolean draggable;

    @NonNull
    private final MarkerIcon icon;

    @Nullable
    private final String iconAnchor;

    @NonNull
    private final String mapId;

    @NonNull
    private final String markerId;

    @Nullable
    private final Double opacity;

    @Nullable
    private final Double rotation;

    public UpdateMarkerByIdOptions(@NonNull PluginCall call) throws Exception {
        JSObject coordinatesObject = call.getObject("coordinates", null);
        String markerId = call.getString("markerId");
        if (markerId == null) {
            throw CustomExceptions.MARKER_ID_MISSING;
        }
        this.animate = Boolean.TRUE.equals(call.getBoolean("animate", false));
        this.animationDuration = call.getInt("animationDuration", 300);
        this.coordinates = coordinatesObject == null ? null : MapLibreHelper.createLatLng(coordinatesObject);
        this.draggable = call.getBoolean("draggable", null);
        this.icon = MarkerIcon.fromObject(call.getData());
        this.iconAnchor = call.getString("iconAnchor");
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
        this.markerId = markerId;
        this.opacity = call.getDouble("opacity");
        this.rotation = call.getDouble("rotation");
    }

    public boolean getAnimate() {
        return animate;
    }

    public int getAnimationDuration() {
        return animationDuration;
    }

    @Nullable
    public LatLng getCoordinates() {
        return coordinates;
    }

    @Nullable
    public Boolean getDraggable() {
        return draggable;
    }

    @NonNull
    public MarkerIcon getIcon() {
        return icon;
    }

    @Nullable
    public String getIconAnchor() {
        return iconAnchor;
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }

    @NonNull
    public String getMarkerId() {
        return markerId;
    }

    @Nullable
    public Double getOpacity() {
        return opacity;
    }

    @Nullable
    public Double getRotation() {
        return rotation;
    }
}
