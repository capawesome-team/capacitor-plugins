package io.capawesome.capacitorjs.plugins.maplibre;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.classes.CustomExceptions;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.maplibre.android.geometry.LatLng;
import org.maplibre.android.geometry.LatLngBounds;
import org.maplibre.android.location.modes.CameraMode;
import org.maplibre.android.style.layers.Property;

public class MapLibreHelper {

    @NonNull
    public static LatLng createLatLng(@NonNull JSObject object) throws Exception {
        Double latitude = MapLibreHelper.getDouble(object, "latitude");
        Double longitude = MapLibreHelper.getDouble(object, "longitude");
        if (latitude == null || longitude == null) {
            throw CustomExceptions.COORDINATES_INVALID;
        }
        return new LatLng(latitude, longitude);
    }

    @NonNull
    public static LatLngBounds createLatLngBounds(@NonNull JSObject object) throws Exception {
        JSObject northeastObject = object.getJSObject("northeast");
        JSObject southwestObject = object.getJSObject("southwest");
        if (northeastObject == null || southwestObject == null) {
            throw CustomExceptions.BOUNDS_MISSING;
        }
        LatLng northeast = MapLibreHelper.createLatLng(northeastObject);
        LatLng southwest = MapLibreHelper.createLatLng(southwestObject);
        return LatLngBounds.from(northeast.getLatitude(), northeast.getLongitude(), southwest.getLatitude(), southwest.getLongitude());
    }

    @NonNull
    public static List<LatLng> createLatLngList(@Nullable JSONArray array) throws Exception {
        if (array == null) {
            throw CustomExceptions.COORDINATES_MISSING;
        }
        List<LatLng> coordinates = new ArrayList<>();
        for (JSObject object : MapLibreHelper.createObjectList(array)) {
            coordinates.add(MapLibreHelper.createLatLng(object));
        }
        return coordinates;
    }

    @NonNull
    public static JSObject createLatLngObject(@NonNull LatLng coordinate) {
        JSObject object = new JSObject();
        object.put("latitude", coordinate.getLatitude());
        object.put("longitude", coordinate.getLongitude());
        return object;
    }

    @NonNull
    public static List<JSObject> createObjectList(@NonNull JSONArray array) throws Exception {
        List<JSObject> objects = new ArrayList<>();
        for (int index = 0; index < array.length(); index++) {
            objects.add(JSObject.fromJSONObject(array.getJSONObject(index)));
        }
        return objects;
    }

    @NonNull
    public static List<String> createStringList(@NonNull JSONArray array) throws Exception {
        List<String> values = new ArrayList<>();
        for (int index = 0; index < array.length(); index++) {
            values.add(array.getString(index));
        }
        return values;
    }

    @Nullable
    public static Double getDouble(@NonNull JSONObject object, @NonNull String key) {
        double value = object.optDouble(key, Double.NaN);
        return Double.isNaN(value) ? null : value;
    }

    @NonNull
    public static String getMapIdFromCall(@NonNull PluginCall call) throws Exception {
        String mapId = call.getString("mapId");
        if (mapId == null) {
            throw CustomExceptions.MAP_ID_MISSING;
        }
        return mapId;
    }

    public static int parseColor(@NonNull String color) throws Exception {
        try {
            if (color.length() == 9) {
                // Android expects the alpha channel at the beginning of the string.
                return Color.parseColor("#" + color.substring(7) + color.substring(1, 7));
            }
            return Color.parseColor(color);
        } catch (Exception exception) {
            throw CustomExceptions.COLOR_INVALID;
        }
    }

    public static int toCameraMode(@NonNull String trackingMode) {
        switch (trackingMode) {
            case "follow":
                return CameraMode.TRACKING;
            case "followWithCourse":
                return CameraMode.TRACKING_GPS;
            case "followWithHeading":
                return CameraMode.TRACKING_COMPASS;
            default:
                return CameraMode.NONE;
        }
    }

    @NonNull
    public static String toIconAnchor(@NonNull String anchor) {
        switch (anchor) {
            case "center":
                return Property.ICON_ANCHOR_CENTER;
            case "left":
                return Property.ICON_ANCHOR_LEFT;
            case "right":
                return Property.ICON_ANCHOR_RIGHT;
            case "top":
                return Property.ICON_ANCHOR_TOP;
            default:
                return Property.ICON_ANCHOR_BOTTOM;
        }
    }
}
