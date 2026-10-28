package io.capawesome.capacitorjs.plugins.ferrostarnavigation;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.CustomExceptions;

public class FerrostarNavigationHelper {

    @NonNull
    public static String getOsrmResponseJsonFromCall(@NonNull PluginCall call) throws Exception {
        String osrmResponseJson = call.getString("osrmResponseJson");
        if (osrmResponseJson == null) {
            throw CustomExceptions.OSRM_RESPONSE_JSON_MISSING;
        }
        return osrmResponseJson;
    }

    public static int getPolylinePrecisionFromCall(@NonNull PluginCall call) {
        return call.getInt("polylinePrecision", 6);
    }
}
