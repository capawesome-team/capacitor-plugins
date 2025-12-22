package io.capawesome.capacitorjs.plugins.agesignals.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import com.google.android.play.agesignals.AgeSignalsResult;
import com.google.android.play.agesignals.model.AgeSignalsVerificationStatus;
import io.capawesome.capacitorjs.plugins.agesignals.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.agesignals.enums.UserStatus;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SetNextAgeSignalsResultOptions {

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

    public SetNextAgeSignalsResultOptions(@NonNull PluginCall call) throws Exception {
        this.userStatus = SetNextAgeSignalsResultOptions.getUserStatusFromCall(call);
        this.ageLower = call.getInt("ageLower");
        this.ageUpper = call.getInt("ageUpper");
        this.mostRecentApprovalDate = call.getString("mostRecentApprovalDate");
        this.installId = call.getString("installId");
    }

    @NonNull
    public AgeSignalsResult buildAgeSignalsResult() throws Exception {
        AgeSignalsResult.Builder builder = AgeSignalsResult.builder();

        // Set user status as integer
        Integer verificationStatus = mapUserStatusToVerificationStatus(this.userStatus);
        builder.setUserStatus(verificationStatus);

        // Set optional fields
        if (this.ageLower != null) {
            builder.setAgeLower(this.ageLower);
        }
        if (this.ageUpper != null) {
            builder.setAgeUpper(this.ageUpper);
        }
        if (this.mostRecentApprovalDate != null) {
            Date date = parseDateString(this.mostRecentApprovalDate);
            builder.setMostRecentApprovalDate(date);
        }
        if (this.installId != null) {
            builder.setInstallId(this.installId);
        }

        return builder.build();
    }

    @NonNull
    private static UserStatus getUserStatusFromCall(@NonNull PluginCall call) throws Exception {
        String userStatusString = call.getString("userStatus");
        if (userStatusString == null) {
            throw new Exception(CustomExceptions.USER_STATUS_MISSING.getMessage());
        }
        try {
            return UserStatus.valueOf(userStatusString);
        } catch (IllegalArgumentException exception) {
            throw new Exception("Invalid userStatus: " + userStatusString);
        }
    }

    @NonNull
    private Integer mapUserStatusToVerificationStatus(@NonNull UserStatus userStatus) {
        switch (userStatus) {
            case VERIFIED:
                return AgeSignalsVerificationStatus.VERIFIED;
            case SUPERVISED:
                return AgeSignalsVerificationStatus.SUPERVISED;
            case SUPERVISED_APPROVAL_PENDING:
                return AgeSignalsVerificationStatus.SUPERVISED_APPROVAL_PENDING;
            case SUPERVISED_APPROVAL_DENIED:
                return AgeSignalsVerificationStatus.SUPERVISED_APPROVAL_DENIED;
            case UNKNOWN:
                return AgeSignalsVerificationStatus.UNKNOWN;
            default:
                return AgeSignalsVerificationStatus.UNKNOWN;
        }
    }

    @NonNull
    private Date parseDateString(@NonNull String dateString) throws Exception {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date date = format.parse(dateString);
            if (date == null) {
                throw new Exception("Failed to parse date: " + dateString);
            }
            return date;
        } catch (Exception exception) {
            throw new Exception("Invalid date format. Expected yyyy-MM-dd, got: " + dateString);
        }
    }
}
