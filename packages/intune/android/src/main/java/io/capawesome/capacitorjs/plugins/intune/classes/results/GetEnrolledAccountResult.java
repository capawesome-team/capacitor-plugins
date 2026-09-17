package io.capawesome.capacitorjs.plugins.intune.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.intune.interfaces.Result;
import org.json.JSONObject;

public class GetEnrolledAccountResult implements Result {

    @Nullable
    private final String accountId;

    @Nullable
    private final String username;

    public GetEnrolledAccountResult(@Nullable String accountId, @Nullable String username) {
        this.accountId = accountId;
        this.username = username;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        if (accountId == null) {
            result.put("account", JSONObject.NULL);
        } else {
            JSObject account = new JSObject();
            account.put("accountId", accountId);
            account.put("username", username == null ? JSONObject.NULL : username);
            result.put("account", account);
        }
        return result;
    }
}
