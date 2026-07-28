package io.capawesome.capacitorjs.plugins.agesignals.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import com.google.android.play.agesignals.AgeSignalsResult;
import io.capawesome.capacitorjs.plugins.agesignals.enums.AgeRangeSource;
import io.capawesome.capacitorjs.plugins.agesignals.enums.SignificantChangeStatus;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SetNextAgeSignalsResultOptions {

    @Nullable
    private final Integer ageLower;

    @Nullable
    private final AgeRangeSource ageRangeSource;

    @Nullable
    private final Integer ageUpper;

    @Nullable
    private final String installId;

    @Nullable
    private final String significantChangeApprovalDate;

    @Nullable
    private final SignificantChangeStatus significantChangeStatus;

    public SetNextAgeSignalsResultOptions(@NonNull PluginCall call) throws Exception {
        this.ageLower = call.getInt("ageLower");
        this.ageRangeSource = SetNextAgeSignalsResultOptions.getAgeRangeSourceFromCall(call);
        this.ageUpper = call.getInt("ageUpper");
        this.installId = call.getString("installId");
        this.significantChangeApprovalDate = call.getString("significantChangeApprovalDate");
        this.significantChangeStatus = SetNextAgeSignalsResultOptions.getSignificantChangeStatusFromCall(call);
    }

    @NonNull
    public AgeSignalsResult buildAgeSignalsResult() throws Exception {
        AgeSignalsResult.Builder builder = AgeSignalsResult.builder();
        if (this.ageLower != null) {
            builder.setAgeLower(this.ageLower);
        }
        if (this.ageRangeSource != null) {
            builder.setAgeRangeSource(mapAgeRangeSource(this.ageRangeSource));
        }
        if (this.ageUpper != null) {
            builder.setAgeUpper(this.ageUpper);
        }
        if (this.installId != null) {
            builder.setInstallId(this.installId);
        }
        if (this.significantChangeApprovalDate != null) {
            builder.setSignificantChangeApprovalDate(parseDate(this.significantChangeApprovalDate));
        }
        if (this.significantChangeStatus != null) {
            builder.setSignificantChangeStatus(mapSignificantChangeStatus(this.significantChangeStatus));
        }
        return builder.build();
    }

    @Nullable
    private static AgeRangeSource getAgeRangeSourceFromCall(@NonNull PluginCall call) throws Exception {
        String ageRangeSource = call.getString("ageRangeSource");
        if (ageRangeSource == null) {
            return null;
        }
        try {
            return AgeRangeSource.valueOf(ageRangeSource);
        } catch (IllegalArgumentException exception) {
            throw new Exception("Invalid ageRangeSource: " + ageRangeSource);
        }
    }

    @Nullable
    private static SignificantChangeStatus getSignificantChangeStatusFromCall(@NonNull PluginCall call) throws Exception {
        String significantChangeStatus = call.getString("significantChangeStatus");
        if (significantChangeStatus == null) {
            return null;
        }
        try {
            return SignificantChangeStatus.valueOf(significantChangeStatus);
        } catch (IllegalArgumentException exception) {
            throw new Exception("Invalid significantChangeStatus: " + significantChangeStatus);
        }
    }

    private static int mapAgeRangeSource(@NonNull AgeRangeSource ageRangeSource) {
        switch (ageRangeSource) {
            case TIER_A:
                return com.google.android.play.agesignals.model.AgeRangeSource.TIER_A;
            case TIER_B:
                return com.google.android.play.agesignals.model.AgeRangeSource.TIER_B;
            case TIER_C:
                return com.google.android.play.agesignals.model.AgeRangeSource.TIER_C;
            default:
                return com.google.android.play.agesignals.model.AgeRangeSource.TIER_D;
        }
    }

    private static int mapSignificantChangeStatus(@NonNull SignificantChangeStatus significantChangeStatus) {
        switch (significantChangeStatus) {
            case APPROVED:
                return com.google.android.play.agesignals.model.SignificantChangeStatus.APPROVED;
            case DECLINED:
                return com.google.android.play.agesignals.model.SignificantChangeStatus.DECLINED;
            default:
                return com.google.android.play.agesignals.model.SignificantChangeStatus.PENDING;
        }
    }

    @NonNull
    private static Date parseDate(@NonNull String date) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return format.parse(date);
        } catch (ParseException exception) {
            throw new Exception("Invalid significantChangeApprovalDate: " + date);
        }
    }
}
