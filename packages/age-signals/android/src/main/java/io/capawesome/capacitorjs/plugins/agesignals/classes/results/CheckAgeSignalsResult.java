package io.capawesome.capacitorjs.plugins.agesignals.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.google.android.play.agesignals.AgeSignalsResult;
import io.capawesome.capacitorjs.plugins.agesignals.enums.UserStatus;
import io.capawesome.capacitorjs.plugins.agesignals.interfaces.Result;

public class CheckAgeSignalsResult implements Result {

    @NonNull
    private final UserStatus userStatus;

    @Nullable
    private final Integer ageLower;

    @Nullable
    private final Integer ageUpper;

    @Nullable
    private final String mostRecentApprovalDate;

    @Nullable
    private final String installId;

    public CheckAgeSignalsResult(@NonNull AgeSignalsResult ageSignalsResult) {
        this.userStatus = mapUserStatus(ageSignalsResult.userStatus());
        this.ageLower = ageSignalsResult.ageLower();
        this.ageUpper = ageSignalsResult.ageUpper();
        this.mostRecentApprovalDate = ageSignalsResult.mostRecentApprovalDate() != null
            ? ageSignalsResult.mostRecentApprovalDate().toString()
            : null;
        this.installId = ageSignalsResult.installId();
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("userStatus", userStatus.name());
        if (ageLower != null) {
            result.put("ageLower", ageLower);
        }
        if (ageUpper != null) {
            result.put("ageUpper", ageUpper);
        }
        if (mostRecentApprovalDate != null) {
            result.put("mostRecentApprovalDate", mostRecentApprovalDate);
        }
        if (installId != null) {
            result.put("installId", installId);
        }
        return result;
    }

    @NonNull
    private UserStatus mapUserStatus(@Nullable Integer status) {
        if (status == null) {
            return UserStatus.EMPTY;
        }

        UserStatus[] values = UserStatus.values();

        if (status < 0 || status >= values.length) {
            throw new IllegalArgumentException("Invalid UserStatus: " + status);
        }

        return values[status];
    }
}
