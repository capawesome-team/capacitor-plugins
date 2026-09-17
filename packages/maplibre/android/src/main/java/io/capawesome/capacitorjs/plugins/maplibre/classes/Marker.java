package io.capawesome.capacitorjs.plugins.maplibre.classes;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import org.maplibre.android.geometry.LatLng;

public class Marker {

    @NonNull
    private final LatLng coordinates;

    private final boolean draggable;

    @NonNull
    private final String iconAnchor;

    @NonNull
    private final MarkerIcon icon;

    @NonNull
    private final String id;

    private final double opacity;
    private final double rotation;

    public Marker(@NonNull JSObject object) throws Exception {
        JSObject coordinatesObject = object.getJSObject("coordinates");
        String id = object.getString("id", null);
        if (coordinatesObject == null) {
            throw CustomExceptions.COORDINATES_MISSING;
        }
        if (id == null) {
            throw CustomExceptions.MARKER_ID_MISSING;
        }
        this.coordinates = MapLibreHelper.createLatLng(coordinatesObject);
        this.draggable = object.optBoolean("draggable", false);
        this.icon = MarkerIcon.fromObject(object);
        this.iconAnchor = object.getString("iconAnchor", "bottom");
        this.id = id;
        this.opacity = object.optDouble("opacity", 1);
        this.rotation = object.optDouble("rotation", 0);
    }

    @NonNull
    public LatLng getCoordinates() {
        return coordinates;
    }

    public boolean getDraggable() {
        return draggable;
    }

    @NonNull
    public MarkerIcon getIcon() {
        return icon;
    }

    @NonNull
    public String getIconAnchor() {
        return iconAnchor;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public double getOpacity() {
        return opacity;
    }

    public double getRotation() {
        return rotation;
    }
}
