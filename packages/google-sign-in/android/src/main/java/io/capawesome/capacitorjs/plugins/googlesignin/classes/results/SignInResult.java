package io.capawesome.capacitorjs.plugins.googlesignin.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.googlesignin.interfaces.Result;
import org.json.JSONObject;

public class SignInResult implements Result {

    @NonNull
    private final String idToken;

    @NonNull
    private final String userId;

    @Nullable
    private final String email;

    @Nullable
    private final String displayName;

    @Nullable
    private final String givenName;

    @Nullable
    private final String familyName;

    @Nullable
    private final String imageUrl;

    @Nullable
    private final String accessToken;

    @Nullable
    private final String serverAuthCode;

    public SignInResult(
        @NonNull String idToken,
        @NonNull String userId,
        @Nullable String email,
        @Nullable String displayName,
        @Nullable String givenName,
        @Nullable String familyName,
        @Nullable String imageUrl,
        @Nullable String accessToken,
        @Nullable String serverAuthCode
    ) {
        this.idToken = idToken;
        this.userId = userId;
        this.email = email;
        this.displayName = displayName;
        this.givenName = givenName;
        this.familyName = familyName;
        this.imageUrl = imageUrl;
        this.accessToken = accessToken;
        this.serverAuthCode = serverAuthCode;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("idToken", idToken);
        result.put("userId", userId);
        result.put("email", email == null ? JSONObject.NULL : email);
        result.put("displayName", displayName == null ? JSONObject.NULL : displayName);
        result.put("givenName", givenName == null ? JSONObject.NULL : givenName);
        result.put("familyName", familyName == null ? JSONObject.NULL : familyName);
        result.put("imageUrl", imageUrl == null ? JSONObject.NULL : imageUrl);
        result.put("accessToken", accessToken == null ? JSONObject.NULL : accessToken);
        result.put("serverAuthCode", serverAuthCode == null ? JSONObject.NULL : serverAuthCode);
        return result;
    }
}
