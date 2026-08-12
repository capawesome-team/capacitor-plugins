package io.capawesome.capacitorjs.plugins.facebooksignin.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.facebook.AccessToken;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.facebooksignin.FacebookSignInHelper;
import io.capawesome.capacitorjs.plugins.facebooksignin.interfaces.Result;
import org.json.JSONObject;

public class SignInResult implements Result {

    @Nullable
    private final AccessToken accessToken;

    @Nullable
    private final String authenticationToken;

    @Nullable
    private final String profileEmail;

    @NonNull
    private final String profileId;

    @Nullable
    private final String profileImageUrl;

    @Nullable
    private final String profileName;

    public SignInResult(
        @Nullable AccessToken accessToken,
        @Nullable String authenticationToken,
        @NonNull String profileId,
        @Nullable String profileName,
        @Nullable String profileEmail,
        @Nullable String profileImageUrl
    ) {
        this.accessToken = accessToken;
        this.authenticationToken = authenticationToken;
        this.profileId = profileId;
        this.profileName = profileName;
        this.profileEmail = profileEmail;
        this.profileImageUrl = profileImageUrl;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject profile = new JSObject();
        profile.put("email", profileEmail == null ? JSONObject.NULL : profileEmail);
        profile.put("id", profileId);
        profile.put("imageUrl", profileImageUrl == null ? JSONObject.NULL : profileImageUrl);
        profile.put("name", profileName == null ? JSONObject.NULL : profileName);

        JSObject result = new JSObject();
        result.put("accessToken", accessToken == null ? JSONObject.NULL : FacebookSignInHelper.createAccessTokenJSObject(accessToken));
        result.put("authenticationToken", authenticationToken == null ? JSONObject.NULL : authenticationToken);
        result.put("profile", profile);
        return result;
    }
}
