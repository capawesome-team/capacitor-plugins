package io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.FerrostarNavigationHelper;

public class ReplaceRouteOptions {

    @NonNull
    private final String osrmResponseJson;

    private final int polylinePrecision;

    public ReplaceRouteOptions(@NonNull PluginCall call) throws Exception {
        this.osrmResponseJson = FerrostarNavigationHelper.getOsrmResponseJsonFromCall(call);
        this.polylinePrecision = FerrostarNavigationHelper.getPolylinePrecisionFromCall(call);
    }

    @NonNull
    public String getOsrmResponseJson() {
        return osrmResponseJson;
    }

    public int getPolylinePrecision() {
        return polylinePrecision;
    }
}
