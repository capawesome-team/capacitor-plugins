package io.capawesome.capacitorjs.plugins.agesignals.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import com.google.android.play.agesignals.AgeSignalsAccessResult;
import com.google.android.play.agesignals.model.AgeSignalsStatus;
import io.capawesome.capacitorjs.plugins.agesignals.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.agesignals.enums.AgeRangeStatus;

public class SetNextAgeSignalsAccessResultOptions {

    @NonNull
    private final AgeRangeStatus status;

    public SetNextAgeSignalsAccessResultOptions(@NonNull PluginCall call) throws Exception {
        this.status = SetNextAgeSignalsAccessResultOptions.getStatusFromCall(call);
    }

    @NonNull
    public AgeSignalsAccessResult buildAgeSignalsAccessResult() {
        return AgeSignalsAccessResult.builder().setAgeSignalsStatus(mapStatus(this.status)).build();
    }

    @NonNull
    private static AgeRangeStatus getStatusFromCall(@NonNull PluginCall call) throws Exception {
        String status = call.getString("status");
        if (status == null) {
            throw CustomExceptions.STATUS_MISSING;
        }
        try {
            return AgeRangeStatus.valueOf(status);
        } catch (IllegalArgumentException exception) {
            throw new Exception("Invalid status: " + status);
        }
    }

    private static int mapStatus(@NonNull AgeRangeStatus status) {
        switch (status) {
            case NOT_SHARED:
                return AgeSignalsStatus.NOT_SHARED;
            case SHARED:
                return AgeSignalsStatus.SHARED;
            case VERIFICATION_REQUIRED:
                return AgeSignalsStatus.VERIFICATION_REQUIRED;
            default:
                return AgeSignalsStatus.UNSPECIFIED;
        }
    }
}
