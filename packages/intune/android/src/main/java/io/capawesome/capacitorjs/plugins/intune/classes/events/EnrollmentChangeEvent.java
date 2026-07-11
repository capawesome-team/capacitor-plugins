package io.capawesome.capacitorjs.plugins.intune.classes.events;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.intune.interfaces.Result;
import org.json.JSONObject;

public class EnrollmentChangeEvent implements Result {

    @Nullable
    private final String accountId;

    @NonNull
    private final String status;

    public EnrollmentChangeEvent(@Nullable String accountId, @NonNull String status) {
        this.accountId = accountId;
        this.status = status;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("accountId", accountId == null ? JSONObject.NULL : accountId);
        result.put("status", status);
        return result;
    }
}
