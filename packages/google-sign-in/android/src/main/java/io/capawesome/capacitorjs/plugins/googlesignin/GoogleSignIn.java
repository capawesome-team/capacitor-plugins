package io.capawesome.capacitorjs.plugins.googlesignin;

import android.app.PendingIntent;
import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.credentials.ClearCredentialStateRequest;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import com.google.android.gms.auth.api.identity.AuthorizationRequest;
import com.google.android.gms.auth.api.identity.AuthorizationResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import io.capawesome.capacitorjs.plugins.googlesignin.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.googlesignin.classes.options.InitializeOptions;
import io.capawesome.capacitorjs.plugins.googlesignin.classes.options.SignInOptions;
import io.capawesome.capacitorjs.plugins.googlesignin.classes.results.SignInResult;
import io.capawesome.capacitorjs.plugins.googlesignin.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.googlesignin.interfaces.NonEmptyResultCallback;
import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;

public class GoogleSignIn {

    private final GoogleSignInPlugin plugin;

    @Nullable
    private String clientId;

    @Nullable
    private String[] scopes;

    @Nullable
    private NonEmptyResultCallback<SignInResult> pendingSignInCallback;

    @Nullable
    private String pendingIdToken;

    @Nullable
    private String pendingUserId;

    @Nullable
    private String pendingEmail;

    @Nullable
    private String pendingDisplayName;

    @Nullable
    private String pendingGivenName;

    @Nullable
    private String pendingFamilyName;

    @Nullable
    private String pendingImageUrl;

    public GoogleSignIn(@NonNull GoogleSignInPlugin plugin) {
        this.plugin = plugin;
    }

    public void initialize(@NonNull InitializeOptions options, @NonNull EmptyCallback callback) {
        this.clientId = options.getClientId();
        this.scopes = options.getScopes();
        callback.success();
    }

    public void signIn(@NonNull SignInOptions options, @NonNull NonEmptyResultCallback<SignInResult> callback) {
        if (clientId == null) {
            callback.error(CustomExceptions.NOT_INITIALIZED);
            return;
        }

        GetSignInWithGoogleOption.Builder optionBuilder = new GetSignInWithGoogleOption.Builder(clientId);
        String nonce = options.getNonce();
        if (nonce != null) {
            optionBuilder.setNonce(nonce);
        }
        GetSignInWithGoogleOption signInOption = optionBuilder.build();

        GetCredentialRequest request = new GetCredentialRequest.Builder().addCredentialOption(signInOption).build();

        CredentialManager credentialManager = CredentialManager.create(plugin.getContext());
        credentialManager.getCredentialAsync(
            plugin.getActivity(),
            request,
            null,
            Runnable::run,
            new androidx.credentials.CredentialManagerCallback<
                GetCredentialResponse,
                androidx.credentials.exceptions.GetCredentialException
            >() {
                @Override
                public void onResult(@NonNull GetCredentialResponse response) {
                    handleCredentialResponse(response, callback);
                }

                @Override
                public void onError(@NonNull androidx.credentials.exceptions.GetCredentialException e) {
                    if (e instanceof androidx.credentials.exceptions.GetCredentialCancellationException) {
                        callback.error(CustomExceptions.SIGN_IN_CANCELED);
                    } else {
                        callback.error(new Exception(e.getMessage()));
                    }
                }
            }
        );
    }

    public void signOut(@NonNull EmptyCallback callback) {
        CredentialManager credentialManager = CredentialManager.create(plugin.getContext());
        ClearCredentialStateRequest request = new ClearCredentialStateRequest();
        credentialManager.clearCredentialStateAsync(
            request,
            null,
            Runnable::run,
            new androidx.credentials.CredentialManagerCallback<Void, androidx.credentials.exceptions.ClearCredentialException>() {
                @Override
                public void onResult(@NonNull Void result) {
                    callback.success();
                }

                @Override
                public void onError(@NonNull androidx.credentials.exceptions.ClearCredentialException e) {
                    callback.error(new Exception(e.getMessage()));
                }
            }
        );
    }

    public void handleAuthorizationResult(@NonNull AuthorizationResult authResult) {
        if (pendingSignInCallback == null) {
            return;
        }
        NonEmptyResultCallback<SignInResult> callback = pendingSignInCallback;
        String accessToken = authResult.getAccessToken();
        String serverAuthCode = authResult.getServerAuthCode();
        SignInResult result = new SignInResult(
            pendingIdToken,
            pendingUserId,
            pendingEmail,
            pendingDisplayName,
            pendingGivenName,
            pendingFamilyName,
            pendingImageUrl,
            accessToken,
            serverAuthCode
        );
        clearPendingState();
        callback.success(result);
    }

    public void handleAuthorizationCanceled() {
        if (pendingSignInCallback == null) {
            return;
        }
        NonEmptyResultCallback<SignInResult> callback = pendingSignInCallback;
        clearPendingState();
        callback.error(CustomExceptions.SIGN_IN_CANCELED);
    }

    private void handleCredentialResponse(@NonNull GetCredentialResponse response, @NonNull NonEmptyResultCallback<SignInResult> callback) {
        Credential credential = response.getCredential();
        if (!(credential instanceof CustomCredential)) {
            callback.error(new Exception("Unexpected credential type."));
            return;
        }
        CustomCredential customCredential = (CustomCredential) credential;
        if (!GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL.equals(customCredential.getType())) {
            callback.error(new Exception("Unexpected credential type."));
            return;
        }

        GoogleIdTokenCredential googleCredential = GoogleIdTokenCredential.createFrom(customCredential.getData());
        String idToken = googleCredential.getIdToken();
        String userId = parseUserIdFromIdToken(idToken);
        if (userId == null) {
            callback.error(new Exception("Failed to parse user ID from ID token."));
            return;
        }

        String email = googleCredential.getId();
        String displayName = googleCredential.getDisplayName();
        String givenName = googleCredential.getGivenName();
        String familyName = googleCredential.getFamilyName();
        android.net.Uri profilePictureUri = googleCredential.getProfilePictureUri();
        String imageUrl = profilePictureUri != null ? profilePictureUri.toString() : null;

        if (scopes != null && scopes.length > 0) {
            authorize(idToken, userId, email, displayName, givenName, familyName, imageUrl, callback);
        } else {
            SignInResult result = new SignInResult(idToken, userId, email, displayName, givenName, familyName, imageUrl, null, null);
            callback.success(result);
        }
    }

    private void authorize(
        @NonNull String idToken,
        @NonNull String userId,
        @Nullable String email,
        @Nullable String displayName,
        @Nullable String givenName,
        @Nullable String familyName,
        @Nullable String imageUrl,
        @NonNull NonEmptyResultCallback<SignInResult> callback
    ) {
        List<com.google.android.gms.common.api.Scope> scopeList = new java.util.ArrayList<>();
        for (String scope : scopes) {
            scopeList.add(new com.google.android.gms.common.api.Scope(scope));
        }

        AuthorizationRequest authRequest = AuthorizationRequest.builder()
            .setRequestedScopes(scopeList)
            .requestOfflineAccess(clientId)
            .build();

        Identity.getAuthorizationClient(plugin.getActivity())
            .authorize(authRequest)
            .addOnSuccessListener(authResult -> {
                if (authResult.hasResolution()) {
                    PendingIntent pendingIntent = authResult.getPendingIntent();
                    if (pendingIntent != null) {
                        savePendingState(idToken, userId, email, displayName, givenName, familyName, imageUrl, callback);
                        plugin.launchAuthorizationIntent(pendingIntent);
                    } else {
                        callback.error(new Exception("Authorization resolution has no pending intent."));
                    }
                } else {
                    String accessToken = authResult.getAccessToken();
                    String serverAuthCode = authResult.getServerAuthCode();
                    SignInResult result = new SignInResult(
                        idToken,
                        userId,
                        email,
                        displayName,
                        givenName,
                        familyName,
                        imageUrl,
                        accessToken,
                        serverAuthCode
                    );
                    callback.success(result);
                }
            })
            .addOnFailureListener(e -> {
                callback.error(new Exception(e.getMessage()));
            });
    }

    @Nullable
    private String parseUserIdFromIdToken(@NonNull String idToken) {
        try {
            String[] parts = idToken.split("\\.");
            if (parts.length != 3) {
                return null;
            }
            byte[] decodedBytes = Base64.decode(parts[1], Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);
            String payload = new String(decodedBytes, "UTF-8");
            JSONObject json = new JSONObject(payload);
            return json.optString("sub", null);
        } catch (Exception e) {
            return null;
        }
    }

    private void savePendingState(
        @NonNull String idToken,
        @NonNull String userId,
        @Nullable String email,
        @Nullable String displayName,
        @Nullable String givenName,
        @Nullable String familyName,
        @Nullable String imageUrl,
        @NonNull NonEmptyResultCallback<SignInResult> callback
    ) {
        this.pendingSignInCallback = callback;
        this.pendingIdToken = idToken;
        this.pendingUserId = userId;
        this.pendingEmail = email;
        this.pendingDisplayName = displayName;
        this.pendingGivenName = givenName;
        this.pendingFamilyName = familyName;
        this.pendingImageUrl = imageUrl;
    }

    private void clearPendingState() {
        this.pendingSignInCallback = null;
        this.pendingIdToken = null;
        this.pendingUserId = null;
        this.pendingEmail = null;
        this.pendingDisplayName = null;
        this.pendingGivenName = null;
        this.pendingFamilyName = null;
        this.pendingImageUrl = null;
    }
}
