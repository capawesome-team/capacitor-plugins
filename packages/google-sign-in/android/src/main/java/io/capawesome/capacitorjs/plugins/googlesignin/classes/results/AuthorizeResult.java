package io.capawesome.capacitorjs.plugins.googlesignin.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.googlesignin.interfaces.Result;
import org.json.JSONObject;

public class AuthorizeResult implements Result {

    @NonNull
    private final String accessToken;

    @Nullable
    private final String serverAuthCode;

    public AuthorizeResult(@NonNull String accessToken, @Nullable String serverAuthCode) {
        this.accessToken = accessToken;
        this.serverAuthCode = serverAuthCode;
    }

    @NonNull
    public String getAccessToken() {
        return accessToken;
    }

    @Nullable
    public String getServerAuthCode() {
        return serverAuthCode;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("accessToken", accessToken);
        result.put("serverAuthCode", serverAuthCode == null ? JSONObject.NULL : serverAuthCode);
        return result;
    }
}
