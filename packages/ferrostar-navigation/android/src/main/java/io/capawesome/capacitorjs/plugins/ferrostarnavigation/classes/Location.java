package io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.interfaces.Result;
import org.json.JSONObject;

public class Location implements Result {

    @Nullable
    private final Integer courseDegrees;

    private final double horizontalAccuracyMeters;

    private final double latitude;

    private final double longitude;

    public Location(double latitude, double longitude, @Nullable Integer courseDegrees, double horizontalAccuracyMeters) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.courseDegrees = courseDegrees;
        this.horizontalAccuracyMeters = horizontalAccuracyMeters;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("latitude", latitude);
        result.put("longitude", longitude);
        result.put("courseDegrees", courseDegrees == null ? JSONObject.NULL : courseDegrees);
        result.put("horizontalAccuracyMeters", horizontalAccuracyMeters);
        return result;
    }
}
