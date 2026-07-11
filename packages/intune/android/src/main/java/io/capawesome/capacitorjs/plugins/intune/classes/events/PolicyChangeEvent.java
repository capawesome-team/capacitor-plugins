package io.capawesome.capacitorjs.plugins.intune.classes.events;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.intune.interfaces.Result;
import org.json.JSONObject;

public class PolicyChangeEvent implements Result {

    @Nullable
    private final String accountId;

    public PolicyChangeEvent(@Nullable String accountId) {
        this.accountId = accountId;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("accountId", accountId == null ? JSONObject.NULL : accountId);
        return result;
    }
}
