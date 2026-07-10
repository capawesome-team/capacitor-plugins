package io.capawesome.capacitorjs.plugins.mapslauncher.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.mapslauncher.classes.CustomExceptions;
import org.json.JSONObject;

public class Destination {

    @Nullable
    private final String address;

    @Nullable
    private final Double latitude;

    @Nullable
    private final Double longitude;

    public Destination(@NonNull JSONObject object) throws Exception {
        boolean hasLatitude = object.has("latitude") && !object.isNull("latitude");
        boolean hasLongitude = object.has("longitude") && !object.isNull("longitude");
        boolean hasCoordinates = hasLatitude && hasLongitude;
        String addressValue = object.isNull("address") ? null : object.optString("address", null);
        boolean hasAddress = addressValue != null && !addressValue.trim().isEmpty();
        if (hasCoordinates == hasAddress) {
            throw CustomExceptions.DESTINATION_INVALID;
        }
        if (hasCoordinates) {
            this.latitude = object.getDouble("latitude");
            this.longitude = object.getDouble("longitude");
            this.address = null;
        } else {
            this.latitude = null;
            this.longitude = null;
            this.address = addressValue;
        }
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    @Nullable
    public Double getLatitude() {
        return latitude;
    }

    @Nullable
    public Double getLongitude() {
        return longitude;
    }

    public boolean hasCoordinates() {
        return latitude != null && longitude != null;
    }
}
