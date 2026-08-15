package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.classes.GestureSettings;
import io.capawesome.capacitorjs.plugins.maplibre.classes.MapFrame;
import org.maplibre.android.geometry.LatLng;

public class CreateMapOptions {

    public static final String DEFAULT_STYLE_URL = "https://demotiles.maplibre.org/style.json";

    private final double bearing;

    @Nullable
    private final LatLng center;

    @NonNull
    private final MapFrame frame;

    @NonNull
    private final GestureSettings gestures;

    @NonNull
    private final String mapId;

    @Nullable
    private final Double maxZoom;

    @Nullable
    private final Double minZoom;

    private final double pitch;

    @Nullable
    private final String styleJson;

    @NonNull
    private final String styleUrl;

    private final double zoom;

    public CreateMapOptions(@NonNull PluginCall call) throws Exception {
        JSObject centerObject = call.getObject("center", null);
        this.bearing = call.getDouble("bearing", 0.0);
        this.center = centerObject == null ? null : MapLibreHelper.createLatLng(centerObject);
        this.frame = MapFrame.getFrameFromCall(call);
        this.gestures = new GestureSettings(call.getObject("gestures", new JSObject()));
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
        this.maxZoom = call.getDouble("maxZoom");
        this.minZoom = call.getDouble("minZoom");
        this.pitch = call.getDouble("pitch", 0.0);
        this.styleJson = call.getString("styleJson");
        this.styleUrl = call.getString("styleUrl", DEFAULT_STYLE_URL);
        this.zoom = call.getDouble("zoom", 0.0);
    }

    public double getBearing() {
        return bearing;
    }

    @Nullable
    public LatLng getCenter() {
        return center;
    }

    @NonNull
    public MapFrame getFrame() {
        return frame;
    }

    @NonNull
    public GestureSettings getGestures() {
        return gestures;
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }

    @Nullable
    public Double getMaxZoom() {
        return maxZoom;
    }

    @Nullable
    public Double getMinZoom() {
        return minZoom;
    }

    public double getPitch() {
        return pitch;
    }

    @Nullable
    public String getStyleJson() {
        return styleJson;
    }

    @NonNull
    public String getStyleUrl() {
        return styleUrl;
    }

    public double getZoom() {
        return zoom;
    }
}
