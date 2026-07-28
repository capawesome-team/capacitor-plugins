package io.capawesome.capacitorjs.plugins.agesignals.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.google.android.play.agesignals.AgeSignalsResult;
import io.capawesome.capacitorjs.plugins.agesignals.enums.AgeRangeSource;
import io.capawesome.capacitorjs.plugins.agesignals.enums.SignificantChangeStatus;
import io.capawesome.capacitorjs.plugins.agesignals.interfaces.Result;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class GetAgeRangeResult implements Result {

    @Nullable
    private final Integer ageLower;

    @Nullable
    private final Integer ageUpper;

    @Nullable
    private final AgeRangeSource ageRangeSource;

    @Nullable
    private final String installId;

    @Nullable
    private final SignificantChangeStatus significantChangeStatus;

    @Nullable
    private final Date significantChangeApprovalDate;

    public GetAgeRangeResult(@NonNull AgeSignalsResult result) {
        this.ageLower = result.ageLower();
        this.ageUpper = result.ageUpper();
        this.ageRangeSource = mapAgeRangeSource(result.ageRangeSource());
        this.installId = result.installId();
        this.significantChangeStatus = mapSignificantChangeStatus(result.significantChangeStatus());
        this.significantChangeApprovalDate = result.significantChangeApprovalDate();
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        JSObject ageRange = createAgeRange();
        if (ageRange != null) {
            result.put("ageRange", ageRange);
        }
        if (installId != null) {
            result.put("installId", installId);
        }
        JSObject significantChange = createSignificantChange();
        if (significantChange != null) {
            result.put("significantChange", significantChange);
        }
        return result;
    }

    @Nullable
    private JSObject createAgeRange() {
        if (ageLower == null && ageUpper == null && ageRangeSource == null) {
            return null;
        }
        JSObject ageRange = new JSObject();
        if (ageLower != null) {
            ageRange.put("lowerBound", ageLower);
        }
        if (ageUpper != null) {
            ageRange.put("upperBound", ageUpper);
        }
        if (ageRangeSource != null) {
            ageRange.put("ageRangeSource", ageRangeSource.name());
        }
        return ageRange;
    }

    @Nullable
    private JSObject createSignificantChange() {
        if (significantChangeStatus == null) {
            return null;
        }
        JSObject significantChange = new JSObject();
        significantChange.put("status", significantChangeStatus.name());
        if (significantChangeApprovalDate != null) {
            significantChange.put("approvalDate", formatDate(significantChangeApprovalDate));
        }
        return significantChange;
    }

    @NonNull
    private static String formatDate(@NonNull Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.format(date);
    }

    @Nullable
    private static AgeRangeSource mapAgeRangeSource(@Nullable Integer source) {
        if (source == null) {
            return null;
        }
        switch (source) {
            case com.google.android.play.agesignals.model.AgeRangeSource.TIER_A:
                return AgeRangeSource.TIER_A;
            case com.google.android.play.agesignals.model.AgeRangeSource.TIER_B:
                return AgeRangeSource.TIER_B;
            case com.google.android.play.agesignals.model.AgeRangeSource.TIER_C:
                return AgeRangeSource.TIER_C;
            case com.google.android.play.agesignals.model.AgeRangeSource.TIER_D:
                return AgeRangeSource.TIER_D;
            default:
                return null;
        }
    }

    @Nullable
    private static SignificantChangeStatus mapSignificantChangeStatus(@Nullable Integer status) {
        if (status == null) {
            return null;
        }
        switch (status) {
            case com.google.android.play.agesignals.model.SignificantChangeStatus.APPROVED:
                return SignificantChangeStatus.APPROVED;
            case com.google.android.play.agesignals.model.SignificantChangeStatus.DECLINED:
                return SignificantChangeStatus.DECLINED;
            case com.google.android.play.agesignals.model.SignificantChangeStatus.PENDING:
                return SignificantChangeStatus.PENDING;
            default:
                return null;
        }
    }
}
