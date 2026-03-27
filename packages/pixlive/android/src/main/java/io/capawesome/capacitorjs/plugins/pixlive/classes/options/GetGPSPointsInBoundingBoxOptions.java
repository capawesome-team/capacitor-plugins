package io.capawesome.capacitorjs.plugins.pixlive.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.pixlive.classes.CustomExceptions;

public class GetGPSPointsInBoundingBoxOptions {

    private final double minLatitude;
    private final double minLongitude;
    private final double maxLatitude;
    private final double maxLongitude;

    public GetGPSPointsInBoundingBoxOptions(@NonNull PluginCall call) throws Exception {
        this.minLatitude = GetGPSPointsInBoundingBoxOptions.getMinLatitudeFromCall(call);
        this.minLongitude = GetGPSPointsInBoundingBoxOptions.getMinLongitudeFromCall(call);
        this.maxLatitude = GetGPSPointsInBoundingBoxOptions.getMaxLatitudeFromCall(call);
        this.maxLongitude = GetGPSPointsInBoundingBoxOptions.getMaxLongitudeFromCall(call);
    }

    public double getMinLatitude() {
        return minLatitude;
    }

    public double getMinLongitude() {
        return minLongitude;
    }

    public double getMaxLatitude() {
        return maxLatitude;
    }

    public double getMaxLongitude() {
        return maxLongitude;
    }

    private static double getMinLatitudeFromCall(@NonNull PluginCall call) throws Exception {
        Double minLatitude = call.getDouble("minLatitude");
        if (minLatitude == null) {
            throw CustomExceptions.MIN_LATITUDE_MISSING;
        }
        return minLatitude;
    }

    private static double getMinLongitudeFromCall(@NonNull PluginCall call) throws Exception {
        Double minLongitude = call.getDouble("minLongitude");
        if (minLongitude == null) {
            throw CustomExceptions.MIN_LONGITUDE_MISSING;
        }
        return minLongitude;
    }

    private static double getMaxLatitudeFromCall(@NonNull PluginCall call) throws Exception {
        Double maxLatitude = call.getDouble("maxLatitude");
        if (maxLatitude == null) {
            throw CustomExceptions.MAX_LATITUDE_MISSING;
        }
        return maxLatitude;
    }

    private static double getMaxLongitudeFromCall(@NonNull PluginCall call) throws Exception {
        Double maxLongitude = call.getDouble("maxLongitude");
        if (maxLongitude == null) {
            throw CustomExceptions.MAX_LONGITUDE_MISSING;
        }
        return maxLongitude;
    }
}
