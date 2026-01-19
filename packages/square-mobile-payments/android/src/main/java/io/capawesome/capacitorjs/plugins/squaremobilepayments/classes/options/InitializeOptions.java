package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.CustomExceptions;

public class InitializeOptions {

    @NonNull
    private final String locationId;

    public InitializeOptions(@NonNull PluginCall call) throws Exception {
        this.locationId = InitializeOptions.getLocationIdFromCall(call);
    }

    @NonNull
    public String getLocationId() {
        return locationId;
    }

    @NonNull
    private static String getLocationIdFromCall(@NonNull PluginCall call) throws Exception {
        String locationId = call.getString("locationId");
        if (locationId == null) {
            throw CustomExceptions.LOCATION_ID_MISSING;
        }
        return locationId;
    }
}
