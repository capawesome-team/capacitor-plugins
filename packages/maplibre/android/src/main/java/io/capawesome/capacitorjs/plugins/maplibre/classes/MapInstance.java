package io.capawesome.capacitorjs.plugins.maplibre.classes;

import android.animation.ValueAnimator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.maplibre.interfaces.EmptyCallback;
import java.util.HashMap;
import java.util.HashSet;
import org.maplibre.android.location.CompassListener;
import org.maplibre.android.location.engine.LocationEngineCallback;
import org.maplibre.android.location.engine.LocationEngineResult;
import org.maplibre.android.maps.MapLibreMap;
import org.maplibre.android.maps.MapView;
import org.maplibre.android.plugins.annotation.Line;
import org.maplibre.android.plugins.annotation.LineManager;
import org.maplibre.android.plugins.annotation.Symbol;
import org.maplibre.android.plugins.annotation.SymbolManager;

/**
 * Holds the state of a single map.
 */
public class MapInstance {

    @NonNull
    private final HashSet<String> iconKeys = new HashSet<>();

    @NonNull
    private final String mapId;

    @NonNull
    private final MapView mapView;

    @NonNull
    private final HashMap<String, ValueAnimator> markerAnimators = new HashMap<>();

    @NonNull
    private final HashMap<String, MarkerIcon> markerIcons = new HashMap<>();

    @NonNull
    private final HashMap<String, Symbol> markers = new HashMap<>();

    @NonNull
    private final HashMap<String, Line> polylines = new HashMap<>();

    @Nullable
    private CompassListener compassListener;

    @NonNull
    private MapFrame frame;

    @Nullable
    private Float heading;

    @Nullable
    private LineManager lineManager;

    @Nullable
    private LocationEngineCallback<LocationEngineResult> locationCallback;

    @Nullable
    private MapLibreMap map;

    @Nullable
    private EmptyCallback styleLoadCallback;

    @Nullable
    private SymbolManager symbolManager;

    private boolean userLocationEnabled = false;

    public MapInstance(@NonNull String mapId, @NonNull MapView mapView, @NonNull MapFrame frame) {
        this.frame = frame;
        this.mapId = mapId;
        this.mapView = mapView;
    }

    public void addIconKey(@NonNull String iconKey) {
        iconKeys.add(iconKey);
    }

    public void cancelMarkerAnimation(@NonNull String markerId) {
        ValueAnimator animator = markerAnimators.remove(markerId);
        if (animator != null) {
            animator.cancel();
        }
    }

    public void cancelMarkerAnimations() {
        for (ValueAnimator animator : markerAnimators.values()) {
            animator.cancel();
        }
        markerAnimators.clear();
    }

    @Nullable
    public CompassListener getCompassListener() {
        return compassListener;
    }

    @NonNull
    public MapFrame getFrame() {
        return frame;
    }

    @Nullable
    public Float getHeading() {
        return heading;
    }

    @Nullable
    public LineManager getLineManager() {
        return lineManager;
    }

    @Nullable
    public LocationEngineCallback<LocationEngineResult> getLocationCallback() {
        return locationCallback;
    }

    @Nullable
    public MapLibreMap getMap() {
        return map;
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }

    @NonNull
    public MapView getMapView() {
        return mapView;
    }

    @Nullable
    public Symbol getMarker(@NonNull String markerId) {
        return markers.get(markerId);
    }

    @Nullable
    public MarkerIcon getMarkerIcon(@NonNull String markerId) {
        return markerIcons.get(markerId);
    }

    @Nullable
    public Line getPolyline(@NonNull String polylineId) {
        return polylines.get(polylineId);
    }

    @Nullable
    public SymbolManager getSymbolManager() {
        return symbolManager;
    }

    public boolean hasIconKey(@NonNull String iconKey) {
        return iconKeys.contains(iconKey);
    }

    public boolean isUserLocationEnabled() {
        return userLocationEnabled;
    }

    /**
     * Notifies the callback that is waiting for the style of the map to be loaded.
     */
    public void notifyStyleLoadResult(@Nullable Exception exception) {
        EmptyCallback callback = styleLoadCallback;
        if (callback == null) {
            return;
        }
        styleLoadCallback = null;
        if (exception == null) {
            callback.success();
        } else {
            callback.error(exception);
        }
    }

    public void putMarker(@NonNull String markerId, @NonNull Symbol marker, @NonNull MarkerIcon icon) {
        markers.put(markerId, marker);
        markerIcons.put(markerId, icon);
    }

    public void putMarkerAnimator(@NonNull String markerId, @NonNull ValueAnimator animator) {
        markerAnimators.put(markerId, animator);
    }

    public void putPolyline(@NonNull String polylineId, @NonNull Line polyline) {
        polylines.put(polylineId, polyline);
    }

    public void removeMarker(@NonNull String markerId) {
        cancelMarkerAnimation(markerId);
        markerIcons.remove(markerId);
        markers.remove(markerId);
    }

    public void removeMarkers() {
        cancelMarkerAnimations();
        markerIcons.clear();
        markers.clear();
    }

    public void removePolyline(@NonNull String polylineId) {
        polylines.remove(polylineId);
    }

    public void removePolylines() {
        polylines.clear();
    }

    public void removeStyleResources() {
        iconKeys.clear();
        removeMarkers();
        removePolylines();
    }

    public void setCompassListener(@Nullable CompassListener compassListener) {
        this.compassListener = compassListener;
    }

    public void setFrame(@NonNull MapFrame frame) {
        this.frame = frame;
    }

    public void setHeading(@Nullable Float heading) {
        this.heading = heading;
    }

    public void setLineManager(@Nullable LineManager lineManager) {
        this.lineManager = lineManager;
    }

    public void setLocationCallback(@Nullable LocationEngineCallback<LocationEngineResult> locationCallback) {
        this.locationCallback = locationCallback;
    }

    public void setMap(@NonNull MapLibreMap map) {
        this.map = map;
    }

    public void setStyleLoadCallback(@NonNull EmptyCallback styleLoadCallback) {
        this.styleLoadCallback = styleLoadCallback;
    }

    public void setSymbolManager(@Nullable SymbolManager symbolManager) {
        this.symbolManager = symbolManager;
    }

    public void setUserLocationEnabled(boolean userLocationEnabled) {
        this.userLocationEnabled = userLocationEnabled;
    }
}
