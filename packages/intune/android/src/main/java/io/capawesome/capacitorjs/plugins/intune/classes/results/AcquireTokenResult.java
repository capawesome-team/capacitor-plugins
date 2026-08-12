package io.capawesome.capacitorjs.plugins.intune.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.intune.interfaces.Result;
import org.json.JSONObject;

public class AcquireTokenResult implements Result {

    @NonNull
    private final String accessToken;

    @NonNull
    private final String accountId;

    @Nullable
    private final String idToken;

    @Nullable
    private final String tenantId;

    @Nullable
    private final String username;

    public AcquireTokenResult(
        @NonNull String accessToken,
        @NonNull String accountId,
        @Nullable String idToken,
        @Nullable String tenantId,
        @Nullable String username
    ) {
        this.accessToken = accessToken;
        this.accountId = accountId;
        this.idToken = idToken;
        this.tenantId = tenantId;
        this.username = username;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("accessToken", accessToken);
        result.put("accountId", accountId);
        result.put("idToken", idToken == null ? JSONObject.NULL : idToken);
        result.put("tenantId", tenantId == null ? JSONObject.NULL : tenantId);
        result.put("username", username == null ? JSONObject.NULL : username);
        return result;
    }
}
