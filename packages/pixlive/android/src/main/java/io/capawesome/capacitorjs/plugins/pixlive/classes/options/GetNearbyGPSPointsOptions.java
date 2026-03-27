package io.capawesome.capacitorjs.plugins.pixlive.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.pixlive.classes.CustomExceptions;

public class GetNearbyGPSPointsOptions {

    private final double latitude;
    private final double longitude;

    public GetNearbyGPSPointsOptions(@NonNull PluginCall call) throws Exception {
        this.latitude = GetNearbyGPSPointsOptions.getLatitudeFromCall(call);
        this.longitude = GetNearbyGPSPointsOptions.getLongitudeFromCall(call);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    private static double getLatitudeFromCall(@NonNull PluginCall call) throws Exception {
        Double latitude = call.getDouble("latitude");
        if (latitude == null) {
            throw CustomExceptions.LATITUDE_MISSING;
        }
        return latitude;
    }

    private static double getLongitudeFromCall(@NonNull PluginCall call) throws Exception {
        Double longitude = call.getDouble("longitude");
        if (longitude == null) {
            throw CustomExceptions.LONGITUDE_MISSING;
        }
        return longitude;
    }
}
