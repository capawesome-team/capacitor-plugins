package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.interfaces.Result;
import java.util.List;
import org.json.JSONObject;

public class ReaderInfo implements Result {

    @NonNull
    private final String serialNumber;

    @NonNull
    private final String model;

    @NonNull
    private final String status;

    @Nullable
    private final String firmwareVersion;

    @Nullable
    private final Integer batteryLevel;

    @Nullable
    private final Boolean isCharging;

    @NonNull
    private final List<String> supportedCardInputMethods;

    @Nullable
    private final UnavailableReasonInfo unavailableReasonInfo;

    public ReaderInfo(
        @NonNull String serialNumber,
        @NonNull String model,
        @NonNull String status,
        @Nullable String firmwareVersion,
        @Nullable Integer batteryLevel,
        @Nullable Boolean isCharging,
        @NonNull List<String> supportedCardInputMethods,
        @Nullable UnavailableReasonInfo unavailableReasonInfo
    ) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.status = status;
        this.firmwareVersion = firmwareVersion;
        this.batteryLevel = batteryLevel;
        this.isCharging = isCharging;
        this.supportedCardInputMethods = supportedCardInputMethods;
        this.unavailableReasonInfo = unavailableReasonInfo;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("serialNumber", serialNumber);
        result.put("model", model);
        result.put("status", status);
        result.put("firmwareVersion", firmwareVersion == null ? JSONObject.NULL : firmwareVersion);
        result.put("batteryLevel", batteryLevel == null ? JSONObject.NULL : batteryLevel);
        result.put("isCharging", isCharging == null ? JSONObject.NULL : isCharging);

        JSArray methodsArray = new JSArray();
        for (String method : supportedCardInputMethods) {
            methodsArray.put(method);
        }
        result.put("supportedCardInputMethods", methodsArray);

        if (unavailableReasonInfo != null) {
            result.put("unavailableReasonInfo", unavailableReasonInfo.toJSObject());
        } else {
            result.put("unavailableReasonInfo", JSONObject.NULL);
        }

        return result;
    }
}
