package io.capawesome.capacitorjs.plugins.applesignin.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.applesignin.interfaces.Result;
import org.json.JSONObject;

public class SignInResult implements Result {

    @NonNull
    private final String authorizationCode;

    @NonNull
    private final String identityToken;

    @NonNull
    private final String user;

    @Nullable
    private final String email;

    @Nullable
    private final String givenName;

    @Nullable
    private final String familyName;

    @Nullable
    private final String state;

    public SignInResult(
        @NonNull String authorizationCode,
        @NonNull String identityToken,
        @NonNull String user,
        @Nullable String email,
        @Nullable String givenName,
        @Nullable String familyName,
        @Nullable String state
    ) {
        this.authorizationCode = authorizationCode;
        this.identityToken = identityToken;
        this.user = user;
        this.email = email;
        this.givenName = givenName;
        this.familyName = familyName;
        this.state = state;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("authorizationCode", authorizationCode);
        result.put("identityToken", identityToken);
        result.put("user", user);
        result.put("email", email == null ? JSONObject.NULL : email);
        result.put("givenName", givenName == null ? JSONObject.NULL : givenName);
        result.put("familyName", familyName == null ? JSONObject.NULL : familyName);
        if (state != null) {
            result.put("state", state);
        }
        return result;
    }
}
