package io.capawesome.capacitorjs.plugins.applesignin;

import android.content.Intent;
import android.net.Uri;
import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.applesignin.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.applesignin.classes.options.SignInOptions;
import io.capawesome.capacitorjs.plugins.applesignin.classes.results.SignInResult;
import io.capawesome.capacitorjs.plugins.applesignin.interfaces.NonEmptyResultCallback;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.json.JSONObject;

public class AppleSignIn {

    private static final String AUTH_URL = "https://appleid.apple.com/auth/authorize";

    @Nullable
    private String clientId;

    public void initialize(@NonNull String clientId) {
        this.clientId = clientId;
    }

    @NonNull
    public String buildAuthUrl(@NonNull SignInOptions options) throws Exception {
        if (clientId == null) {
            throw CustomExceptions.CLIENT_ID_MISSING;
        }

        Uri.Builder builder = Uri.parse(AUTH_URL)
            .buildUpon()
            .appendQueryParameter("client_id", clientId)
            .appendQueryParameter("redirect_uri", options.getRedirectUrl())
            .appendQueryParameter("response_type", "code id_token")
            .appendQueryParameter("response_mode", "form_post");

        List<String> scopes = options.getScopes();
        if (!scopes.isEmpty()) {
            builder.appendQueryParameter("scope", String.join(" ", scopes));
        }

        String nonce = options.getNonce();
        if (nonce != null) {
            builder.appendQueryParameter("nonce", nonce);
        }

        String state = options.getState();
        if (state != null) {
            builder.appendQueryParameter("state", state);
        }

        return builder.build().toString();
    }

    public void handleActivityResult(int resultCode, @Nullable Intent data, @NonNull NonEmptyResultCallback<SignInResult> callback) {
        if (resultCode != android.app.Activity.RESULT_OK || data == null) {
            callback.error(CustomExceptions.SIGN_IN_CANCELED);
            return;
        }

        String authorizationCode = data.getStringExtra(AppleSignInActivity.EXTRA_AUTHORIZATION_CODE);
        String identityToken = data.getStringExtra(AppleSignInActivity.EXTRA_IDENTITY_TOKEN);
        String state = data.getStringExtra(AppleSignInActivity.EXTRA_STATE);
        String email = data.getStringExtra(AppleSignInActivity.EXTRA_EMAIL);
        String givenName = data.getStringExtra(AppleSignInActivity.EXTRA_GIVEN_NAME);
        String familyName = data.getStringExtra(AppleSignInActivity.EXTRA_FAMILY_NAME);

        if (authorizationCode == null || identityToken == null) {
            callback.error(CustomExceptions.SIGN_IN_FAILED);
            return;
        }

        String user = null;
        try {
            JSONObject payload = decodeJwtPayload(identityToken);
            user = payload.getString("sub");
            if (email == null) {
                email = payload.optString("email", null);
            }
        } catch (Exception e) {
            callback.error(CustomExceptions.SIGN_IN_FAILED);
            return;
        }

        SignInResult result = new SignInResult(authorizationCode, identityToken, user, email, givenName, familyName, state);
        callback.success(result);
    }

    @NonNull
    private static JSONObject decodeJwtPayload(@NonNull String jwt) throws Exception {
        String[] parts = jwt.split("\\.");
        byte[] decoded = Base64.decode(parts[1], Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);
        return new JSONObject(new String(decoded, StandardCharsets.UTF_8));
    }
}
