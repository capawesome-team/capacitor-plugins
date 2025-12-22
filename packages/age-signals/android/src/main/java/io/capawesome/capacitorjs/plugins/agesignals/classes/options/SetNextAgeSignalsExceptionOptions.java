package io.capawesome.capacitorjs.plugins.agesignals.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import com.google.android.play.agesignals.AgeSignalsException;
import com.google.android.play.agesignals.model.AgeSignalsErrorCode;

public class SetNextAgeSignalsExceptionOptions {

    @NonNull
    private final String errorCode;

    public SetNextAgeSignalsExceptionOptions(@NonNull PluginCall call) throws Exception {
        String errorCode = call.getString("errorCode");
        if (errorCode == null) {
            throw new Exception("errorCode must be provided.");
        }
        this.errorCode = errorCode;
    }

    @NonNull
    public AgeSignalsException buildAgeSignalsException() throws Exception {
        int errorCodeInt = mapErrorCodeToAgeSignalsErrorCode(this.errorCode);
        return new AgeSignalsException(errorCodeInt);
    }

    private int mapErrorCodeToAgeSignalsErrorCode(@NonNull String errorCode) throws Exception {
        switch (errorCode) {
            case "API_NOT_AVAILABLE":
                return AgeSignalsErrorCode.API_NOT_AVAILABLE;
            case "PLAY_STORE_NOT_FOUND":
                return AgeSignalsErrorCode.PLAY_STORE_NOT_FOUND;
            case "NETWORK_ERROR":
                return AgeSignalsErrorCode.NETWORK_ERROR;
            case "PLAY_SERVICES_NOT_FOUND":
                return AgeSignalsErrorCode.PLAY_SERVICES_NOT_FOUND;
            case "CANNOT_BIND_TO_SERVICE":
                return AgeSignalsErrorCode.CANNOT_BIND_TO_SERVICE;
            case "PLAY_STORE_VERSION_OUTDATED":
                return AgeSignalsErrorCode.PLAY_STORE_VERSION_OUTDATED;
            case "PLAY_SERVICES_VERSION_OUTDATED":
                return AgeSignalsErrorCode.PLAY_SERVICES_VERSION_OUTDATED;
            case "CLIENT_TRANSIENT_ERROR":
                return AgeSignalsErrorCode.CLIENT_TRANSIENT_ERROR;
            case "APP_NOT_OWNED":
                return AgeSignalsErrorCode.APP_NOT_OWNED;
            case "INTERNAL_ERROR":
                return AgeSignalsErrorCode.INTERNAL_ERROR;
            default:
                throw new Exception("Invalid errorCode: " + errorCode);
        }
    }
}
