package io.capawesome.capacitorjs.plugins.maplibre.classes.events;

import android.location.Location;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.interfaces.Result;
import org.maplibre.android.geometry.LatLng;

public class UserLocationChangeEvent implements Result {

    @NonNull
    private final Location location;

    @NonNull
    private final String mapId;

    @Nullable
    private final Float heading;

    public UserLocationChangeEvent(@Nullable Float heading, @NonNull Location location, @NonNull String mapId) {
        this.heading = heading;
        this.location = location;
        this.mapId = mapId;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        if (location.hasAccuracy()) {
            result.put("accuracy", location.getAccuracy());
        }
        result.put("coordinates", MapLibreHelper.createLatLngObject(new LatLng(location.getLatitude(), location.getLongitude())));
        if (heading != null) {
            result.put("heading", heading.doubleValue());
        }
        result.put("mapId", mapId);
        if (location.hasSpeed()) {
            result.put("speed", location.getSpeed());
        }
        return result;
    }
}
