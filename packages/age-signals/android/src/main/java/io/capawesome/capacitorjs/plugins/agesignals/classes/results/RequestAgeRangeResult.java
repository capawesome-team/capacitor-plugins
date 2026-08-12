package io.capawesome.capacitorjs.plugins.agesignals.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.agesignals.enums.AgeRangeStatus;
import io.capawesome.capacitorjs.plugins.agesignals.interfaces.Result;

public class RequestAgeRangeResult implements Result {

    @NonNull
    private final AgeRangeStatus status;

    @Nullable
    private final GetAgeRangeResult ageRangeResult;

    public RequestAgeRangeResult(@NonNull AgeRangeStatus status, @Nullable GetAgeRangeResult ageRangeResult) {
        this.status = status;
        this.ageRangeResult = ageRangeResult;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = ageRangeResult == null ? new JSObject() : ageRangeResult.toJSObject();
        result.put("status", status.name());
        return result;
    }
}
