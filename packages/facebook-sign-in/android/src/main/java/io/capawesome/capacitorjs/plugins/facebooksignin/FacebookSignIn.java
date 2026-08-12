package io.capawesome.capacitorjs.plugins.facebooksignin;

import android.os.Bundle;
import androidx.activity.result.ActivityResultRegistryOwner;
import androidx.annotation.NonNull;
import com.facebook.AccessToken;
import com.facebook.AuthenticationToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import io.capawesome.capacitorjs.plugins.facebooksignin.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.facebooksignin.classes.options.InitializeOptions;
import io.capawesome.capacitorjs.plugins.facebooksignin.classes.options.SignInOptions;
import io.capawesome.capacitorjs.plugins.facebooksignin.classes.results.GetCurrentAccessTokenResult;
import io.capawesome.capacitorjs.plugins.facebooksignin.classes.results.SignInResult;
import io.capawesome.capacitorjs.plugins.facebooksignin.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.facebooksignin.interfaces.NonEmptyResultCallback;
import org.json.JSONObject;

public class FacebookSignIn {

    private final CallbackManager callbackManager;
    private final FacebookSignInPlugin plugin;

    public FacebookSignIn(@NonNull FacebookSignInPlugin plugin) {
        this.callbackManager = CallbackManager.Factory.create();
        this.plugin = plugin;
    }

    public void getCurrentAccessToken(@NonNull NonEmptyResultCallback<GetCurrentAccessTokenResult> callback) {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && accessToken.isExpired()) {
            accessToken = null;
        }
        callback.success(new GetCurrentAccessTokenResult(accessToken));
    }

    public void initialize(@NonNull InitializeOptions options, @NonNull EmptyCallback callback) {
        String appId = options.getAppId();
        if (appId != null) {
            FacebookSdk.setApplicationId(appId);
        }
        String clientToken = options.getClientToken();
        if (clientToken != null) {
            FacebookSdk.setClientToken(clientToken);
        }
        if (!FacebookSdk.isInitialized()) {
            FacebookSdk.sdkInitialize(plugin.getContext().getApplicationContext());
        }
        callback.success();
    }

    public void signIn(@NonNull SignInOptions options, @NonNull NonEmptyResultCallback<SignInResult> callback) {
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.registerCallback(
            callbackManager,
            new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(@NonNull LoginResult loginResult) {
                    handleSignInSuccess(loginResult, callback);
                }

                @Override
                public void onCancel() {
                    callback.error(CustomExceptions.SIGN_IN_CANCELED);
                }

                @Override
                public void onError(@NonNull FacebookException exception) {
                    callback.error(exception);
                }
            }
        );
        plugin
            .getActivity()
            .runOnUiThread(() ->
                loginManager.logIn((ActivityResultRegistryOwner) plugin.getActivity(), callbackManager, options.getPermissions())
            );
    }

    public void signOut(@NonNull EmptyCallback callback) {
        LoginManager.getInstance().logOut();
        callback.success();
    }

    private void handleSignInSuccess(@NonNull LoginResult loginResult, @NonNull NonEmptyResultCallback<SignInResult> callback) {
        AccessToken accessToken = loginResult.getAccessToken();
        AuthenticationToken authenticationToken = loginResult.getAuthenticationToken();
        GraphRequest request = GraphRequest.newMeRequest(accessToken, (object, response) -> {
            if (response != null && response.getError() != null) {
                callback.error(new Exception(response.getError().getErrorMessage()));
                return;
            }
            String profileId = accessToken.getUserId();
            String profileName = null;
            String profileEmail = null;
            String profileImageUrl = null;
            if (object != null) {
                profileId = object.optString("id", profileId);
                profileName = object.optString("name", null);
                profileEmail = object.optString("email", null);
                JSONObject picture = object.optJSONObject("picture");
                if (picture != null) {
                    JSONObject data = picture.optJSONObject("data");
                    if (data != null) {
                        profileImageUrl = data.optString("url", null);
                    }
                }
            }
            SignInResult result = new SignInResult(
                accessToken,
                authenticationToken == null ? null : authenticationToken.getToken(),
                profileId,
                profileName,
                profileEmail,
                profileImageUrl
            );
            callback.success(result);
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(720).height(720)");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
