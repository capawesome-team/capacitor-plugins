package io.capawesome.capacitorjs.plugins.maplibre.classes;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import java.util.List;
import org.maplibre.android.geometry.LatLng;

public class Polyline {

    public static final String DEFAULT_COLOR = "#3887be";
    public static final double DEFAULT_WIDTH = 4;

    private final int color;

    @NonNull
    private final List<LatLng> coordinates;

    @NonNull
    private final String id;

    private final double opacity;
    private final double width;

    public Polyline(@NonNull JSObject object) throws Exception {
        String id = object.getString("id", null);
        if (id == null) {
            throw CustomExceptions.POLYLINE_ID_MISSING;
        }
        this.color = MapLibreHelper.parseColor(object.getString("color", DEFAULT_COLOR));
        this.coordinates = MapLibreHelper.createLatLngList(object.optJSONArray("coordinates"));
        this.id = id;
        this.opacity = object.optDouble("opacity", 1);
        this.width = object.optDouble("width", DEFAULT_WIDTH);
    }

    public int getColor() {
        return color;
    }

    @NonNull
    public List<LatLng> getCoordinates() {
        return coordinates;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public double getOpacity() {
        return opacity;
    }

    public double getWidth() {
        return width;
    }
}
