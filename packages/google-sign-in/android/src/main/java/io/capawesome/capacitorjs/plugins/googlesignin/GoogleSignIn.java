package io.capawesome.capacitorjs.plugins.googlesignin;

import android.app.PendingIntent;
import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.credentials.ClearCredentialStateRequest;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.ClearCredentialException;
import androidx.credentials.exceptions.GetCredentialCancellationException;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.credentials.exceptions.GetCredentialProviderConfigurationException;
import androidx.credentials.exceptions.NoCredentialException;
import com.google.android.gms.auth.api.identity.AuthorizationRequest;
import com.google.android.gms.auth.api.identity.AuthorizationResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.common.api.Scope;
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import io.capawesome.capacitorjs.plugins.googlesignin.classes.CustomException;
import io.capawesome.capacitorjs.plugins.googlesignin.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.googlesignin.classes.options.AuthorizeOptions;
import io.capawesome.capacitorjs.plugins.googlesignin.classes.options.InitializeOptions;
import io.capawesome.capacitorjs.plugins.googlesignin.classes.options.SignInOptions;
import io.capawesome.capacitorjs.plugins.googlesignin.classes.results.AuthorizeResult;
import io.capawesome.capacitorjs.plugins.googlesignin.classes.results.SignInResult;
import io.capawesome.capacitorjs.plugins.googlesignin.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.googlesignin.interfaces.NonEmptyResultCallback;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class GoogleSignIn {

    @Nullable
    private String clientId;

    @Nullable
    private NonEmptyResultCallback<AuthorizeResult> pendingAuthorizationCallback;

    private final GoogleSignInPlugin plugin;

    @Nullable
    private String[] scopes;

    public GoogleSignIn(@NonNull GoogleSignInPlugin plugin) {
        this.plugin = plugin;
    }

    public void authorize(@NonNull AuthorizeOptions options, @NonNull NonEmptyResultCallback<AuthorizeResult> callback) {
        if (clientId == null) {
            callback.error(CustomExceptions.NOT_INITIALIZED);
            return;
        }
        authorize(options.getScopes(), callback);
    }

    public void handleAuthorizationCanceled() {
        NonEmptyResultCallback<AuthorizeResult> callback = takePendingAuthorizationCallback();
        if (callback == null) {
            return;
        }
        callback.error(CustomExceptions.AUTHORIZATION_CANCELED);
    }

    public void handleAuthorizationFailed(@NonNull Exception exception) {
        NonEmptyResultCallback<AuthorizeResult> callback = takePendingAuthorizationCallback();
        if (callback == null) {
            return;
        }
        callback.error(exception);
    }

    public void handleAuthorizationResult(@NonNull AuthorizationResult authorizationResult) {
        NonEmptyResultCallback<AuthorizeResult> callback = takePendingAuthorizationCallback();
        if (callback == null) {
            return;
        }
        resolveAuthorization(authorizationResult, callback);
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
            new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                @Override
                public void onResult(@NonNull GetCredentialResponse response) {
                    handleCredentialResponse(response, callback);
                }

                @Override
                public void onError(@NonNull GetCredentialException exception) {
                    if (exception instanceof GetCredentialCancellationException) {
                        callback.error(createSignInCanceledException(exception));
                    } else if (exception instanceof NoCredentialException) {
                        callback.error(CustomExceptions.NO_CREDENTIAL_AVAILABLE);
                    } else if (exception instanceof GetCredentialProviderConfigurationException) {
                        callback.error(CustomExceptions.PROVIDER_CONFIGURATION_ERROR);
                    } else {
                        callback.error(createCredentialException(exception.getType(), exception.getErrorMessage(), exception));
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
            new CredentialManagerCallback<Void, ClearCredentialException>() {
                @Override
                public void onResult(@NonNull Void result) {
                    callback.success();
                }

                @Override
                public void onError(@NonNull ClearCredentialException exception) {
                    callback.error(createCredentialException(exception.getType(), exception.getErrorMessage(), exception));
                }
            }
        );
    }

    private void authorize(@NonNull String[] scopes, @NonNull NonEmptyResultCallback<AuthorizeResult> callback) {
        if (pendingAuthorizationCallback != null) {
            callback.error(CustomExceptions.AUTHORIZATION_IN_PROGRESS);
            return;
        }
        pendingAuthorizationCallback = callback;

        List<Scope> scopeList = new ArrayList<>();
        for (String scope : scopes) {
            scopeList.add(new Scope(scope));
        }

        AuthorizationRequest request = AuthorizationRequest.builder().setRequestedScopes(scopeList).requestOfflineAccess(clientId).build();

        Identity.getAuthorizationClient(plugin.getActivity())
            .authorize(request)
            .addOnSuccessListener(authorizationResult -> {
                if (!authorizationResult.hasResolution()) {
                    handleAuthorizationResult(authorizationResult);
                    return;
                }
                PendingIntent pendingIntent = authorizationResult.getPendingIntent();
                if (pendingIntent == null) {
                    handleAuthorizationFailed(new Exception("Authorization resolution has no pending intent."));
                    return;
                }
                try {
                    plugin.launchAuthorizationIntent(pendingIntent);
                } catch (Exception exception) {
                    handleAuthorizationFailed(exception);
                }
            })
            .addOnFailureListener(exception -> handleAuthorizationFailed(new Exception(exception.getMessage(), exception)));
    }

    /**
     * Creates an exception that always exposes the Credential Manager error type,
     * because the error message alone may be absent or too generic to act on.
     */
    @NonNull
    private Exception createCredentialException(@NonNull String type, @Nullable CharSequence errorMessage, @NonNull Exception cause) {
        if (errorMessage == null || errorMessage.length() == 0) {
            return new Exception(type, cause);
        }
        return new Exception(type + ": " + errorMessage, cause);
    }

    /**
     * Creates an exception for a canceled sign-in flow.
     *
     * Google Play services also reports configuration errors (e.g. a missing Android OAuth client)
     * as a cancellation, so the original error message must be preserved to stay diagnosable.
     */
    @NonNull
    private CustomException createSignInCanceledException(@NonNull GetCredentialException exception) {
        CharSequence errorMessage = exception.getErrorMessage();
        if (errorMessage == null || errorMessage.length() == 0) {
            return CustomExceptions.SIGN_IN_CANCELED;
        }
        return new CustomException(CustomExceptions.SIGN_IN_CANCELED.getCode(), errorMessage.toString());
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

        if (scopes == null || scopes.length == 0) {
            callback.success(new SignInResult(idToken, userId, email, displayName, givenName, familyName, imageUrl, null, null));
            return;
        }
        authorize(
            scopes,
            new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull AuthorizeResult result) {
                    callback.success(
                        new SignInResult(
                            idToken,
                            userId,
                            email,
                            displayName,
                            givenName,
                            familyName,
                            imageUrl,
                            result.getAccessToken(),
                            result.getServerAuthCode()
                        )
                    );
                }

                @Override
                public void error(Exception exception) {
                    callback.error(exception == CustomExceptions.AUTHORIZATION_CANCELED ? CustomExceptions.SIGN_IN_CANCELED : exception);
                }
            }
        );
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

    private void resolveAuthorization(
        @NonNull AuthorizationResult authorizationResult,
        @NonNull NonEmptyResultCallback<AuthorizeResult> callback
    ) {
        String accessToken = authorizationResult.getAccessToken();
        if (accessToken == null) {
            callback.error(CustomExceptions.ACCESS_TOKEN_MISSING);
            return;
        }
        callback.success(new AuthorizeResult(accessToken, authorizationResult.getServerAuthCode()));
    }

    @Nullable
    private NonEmptyResultCallback<AuthorizeResult> takePendingAuthorizationCallback() {
        NonEmptyResultCallback<AuthorizeResult> callback = pendingAuthorizationCallback;
        pendingAuthorizationCallback = null;
        return callback;
    }
}
