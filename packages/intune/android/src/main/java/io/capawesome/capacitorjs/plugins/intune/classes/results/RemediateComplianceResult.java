package io.capawesome.capacitorjs.plugins.intune.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.intune.interfaces.Result;
import org.json.JSONObject;

public class RemediateComplianceResult implements Result {

    @Nullable
    private final String errorMessage;

    @Nullable
    private final String errorTitle;

    @NonNull
    private final String status;

    public RemediateComplianceResult(@Nullable String errorMessage, @Nullable String errorTitle, @NonNull String status) {
        this.errorMessage = errorMessage;
        this.errorTitle = errorTitle;
        this.status = status;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("errorMessage", errorMessage == null ? JSONObject.NULL : errorMessage);
        result.put("errorTitle", errorTitle == null ? JSONObject.NULL : errorTitle);
        result.put("status", status);
        return result;
    }
}
