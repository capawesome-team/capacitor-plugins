package io.capawesome.capacitorjs.plugins.appintegrity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.play.core.integrity.IntegrityManagerFactory;
import com.google.android.play.core.integrity.IntegrityServiceException;
import com.google.android.play.core.integrity.IntegrityTokenRequest;
import com.google.android.play.core.integrity.StandardIntegrityException;
import com.google.android.play.core.integrity.StandardIntegrityManager;
import com.google.android.play.core.integrity.model.IntegrityErrorCode;
import com.google.android.play.core.integrity.model.StandardIntegrityErrorCode;
import io.capawesome.capacitorjs.plugins.appintegrity.classes.CustomException;
import io.capawesome.capacitorjs.plugins.appintegrity.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.appintegrity.classes.options.PrepareIntegrityTokenOptions;
import io.capawesome.capacitorjs.plugins.appintegrity.classes.options.RequestIntegrityTokenOptions;
import io.capawesome.capacitorjs.plugins.appintegrity.classes.results.IsAvailableResult;
import io.capawesome.capacitorjs.plugins.appintegrity.classes.results.RequestIntegrityTokenResult;
import io.capawesome.capacitorjs.plugins.appintegrity.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.appintegrity.interfaces.NonEmptyResultCallback;

public class AppIntegrity {

    @NonNull
    private final AppIntegrityPlugin plugin;

    @Nullable
    private StandardIntegrityManager.StandardIntegrityTokenProvider standardIntegrityTokenProvider;

    public AppIntegrity(@NonNull AppIntegrityPlugin plugin) {
        this.plugin = plugin;
    }

    public void isAvailable(@NonNull NonEmptyResultCallback<IsAvailableResult> callback) {
        int status = GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(plugin.getContext());
        callback.success(new IsAvailableResult(status == ConnectionResult.SUCCESS));
    }

    public void prepareIntegrityToken(@NonNull PrepareIntegrityTokenOptions options, @NonNull EmptyCallback callback) {
        StandardIntegrityManager standardIntegrityManager = IntegrityManagerFactory.createStandard(plugin.getContext());
        StandardIntegrityManager.PrepareIntegrityTokenRequest request = StandardIntegrityManager.PrepareIntegrityTokenRequest.builder()
            .setCloudProjectNumber(options.getCloudProjectNumber())
            .build();
        standardIntegrityManager
            .prepareIntegrityToken(request)
            .addOnSuccessListener(provider -> {
                standardIntegrityTokenProvider = provider;
                callback.success();
            })
            .addOnFailureListener(exception -> callback.error(mapException(exception)));
    }

    public void requestIntegrityToken(
        @NonNull RequestIntegrityTokenOptions options,
        @NonNull NonEmptyResultCallback<RequestIntegrityTokenResult> callback
    ) throws Exception {
        String nonce = options.getNonce();
        String requestHash = options.getRequestHash();
        if (requestHash != null) {
            requestStandardIntegrityToken(requestHash, callback);
        } else if (nonce != null) {
            requestClassicIntegrityToken(nonce, options.getCloudProjectNumber(), callback);
        } else {
            throw CustomExceptions.NONCE_OR_REQUEST_HASH_MISSING;
        }
    }

    @NonNull
    private static Exception mapException(@NonNull Exception exception) {
        if (exception instanceof IntegrityServiceException) {
            return mapIntegrityServiceException((IntegrityServiceException) exception);
        }
        if (exception instanceof StandardIntegrityException) {
            return mapStandardIntegrityException((StandardIntegrityException) exception);
        }
        return exception;
    }

    @NonNull
    private static Exception mapIntegrityServiceException(@NonNull IntegrityServiceException exception) {
        switch (exception.getErrorCode()) {
            case IntegrityErrorCode.API_NOT_AVAILABLE:
                return new CustomException("API_NOT_AVAILABLE", "The Play Integrity API is not available on this device.");
            case IntegrityErrorCode.APP_NOT_INSTALLED:
                return new CustomException("APP_NOT_INSTALLED", "The calling app is not installed.");
            case IntegrityErrorCode.APP_UID_MISMATCH:
                return new CustomException("APP_UID_MISMATCH", "The calling app UID does not match the one from the package manager.");
            case IntegrityErrorCode.CANNOT_BIND_TO_SERVICE:
                return new CustomException("CANNOT_BIND_TO_SERVICE", "The app could not bind to the integrity service in the Play Store.");
            case IntegrityErrorCode.CLIENT_TRANSIENT_ERROR:
                return new CustomException(
                    "CLIENT_TRANSIENT_ERROR",
                    "A transient error occurred on the device. Retry with an exponential backoff."
                );
            case IntegrityErrorCode.CLOUD_PROJECT_NUMBER_IS_INVALID:
                return new CustomException(null, "cloudProjectNumber is invalid.");
            case IntegrityErrorCode.GOOGLE_SERVER_UNAVAILABLE:
                return new CustomException(
                    "GOOGLE_SERVER_UNAVAILABLE",
                    "The Google server is currently unavailable. Retry with an exponential backoff."
                );
            case IntegrityErrorCode.INTERNAL_ERROR:
                return new CustomException("INTERNAL_ERROR", "An internal error occurred. Retry with an exponential backoff.");
            case IntegrityErrorCode.NETWORK_ERROR:
                return new CustomException("NETWORK_ERROR", "No network connection is available. Retry when the device is online.");
            case IntegrityErrorCode.NONCE_IS_NOT_BASE64:
                return new CustomException(null, "nonce must be encoded as a base64 web-safe no-wrap string.");
            case IntegrityErrorCode.NONCE_TOO_LONG:
                return new CustomException(null, "nonce must not be longer than 500 bytes.");
            case IntegrityErrorCode.NONCE_TOO_SHORT:
                return new CustomException(null, "nonce must be at least 16 bytes long.");
            case IntegrityErrorCode.PLAY_SERVICES_NOT_FOUND:
                return new CustomException("PLAY_SERVICES_NOT_FOUND", "Google Play Services is not available on this device.");
            case IntegrityErrorCode.PLAY_SERVICES_VERSION_OUTDATED:
                return new CustomException("PLAY_SERVICES_VERSION_OUTDATED", "Google Play Services needs to be updated.");
            case IntegrityErrorCode.PLAY_STORE_NOT_FOUND:
                return new CustomException("PLAY_STORE_NOT_FOUND", "No official Play Store app was found on the device.");
            case IntegrityErrorCode.PLAY_STORE_VERSION_OUTDATED:
                return new CustomException("PLAY_STORE_VERSION_OUTDATED", "The Play Store app needs to be updated.");
            case IntegrityErrorCode.TOO_MANY_REQUESTS:
                return new CustomException("TOO_MANY_REQUESTS", "The calling app is making too many requests and has been throttled.");
            default:
                return exception;
        }
    }

    @NonNull
    private static Exception mapStandardIntegrityException(@NonNull StandardIntegrityException exception) {
        switch (exception.getErrorCode()) {
            case StandardIntegrityErrorCode.API_NOT_AVAILABLE:
                return new CustomException("API_NOT_AVAILABLE", "The Play Integrity API is not available on this device.");
            case StandardIntegrityErrorCode.APP_NOT_INSTALLED:
                return new CustomException("APP_NOT_INSTALLED", "The calling app is not installed.");
            case StandardIntegrityErrorCode.APP_UID_MISMATCH:
                return new CustomException("APP_UID_MISMATCH", "The calling app UID does not match the one from the package manager.");
            case StandardIntegrityErrorCode.CANNOT_BIND_TO_SERVICE:
                return new CustomException("CANNOT_BIND_TO_SERVICE", "The app could not bind to the integrity service in the Play Store.");
            case StandardIntegrityErrorCode.CLIENT_TRANSIENT_ERROR:
                return new CustomException(
                    "CLIENT_TRANSIENT_ERROR",
                    "A transient error occurred on the device. Retry with an exponential backoff."
                );
            case StandardIntegrityErrorCode.CLOUD_PROJECT_NUMBER_IS_INVALID:
                return new CustomException(null, "cloudProjectNumber is invalid.");
            case StandardIntegrityErrorCode.GOOGLE_SERVER_UNAVAILABLE:
                return new CustomException(
                    "GOOGLE_SERVER_UNAVAILABLE",
                    "The Google server is currently unavailable. Retry with an exponential backoff."
                );
            case StandardIntegrityErrorCode.INTEGRITY_TOKEN_PROVIDER_INVALID:
                return new CustomException(
                    "INTEGRITY_TOKEN_PROVIDER_INVALID",
                    "The integrity token provider is invalid. Call prepareIntegrityToken() again."
                );
            case StandardIntegrityErrorCode.INTERNAL_ERROR:
                return new CustomException("INTERNAL_ERROR", "An internal error occurred. Retry with an exponential backoff.");
            case StandardIntegrityErrorCode.NETWORK_ERROR:
                return new CustomException("NETWORK_ERROR", "No network connection is available. Retry when the device is online.");
            case StandardIntegrityErrorCode.PLAY_SERVICES_NOT_FOUND:
                return new CustomException("PLAY_SERVICES_NOT_FOUND", "Google Play Services is not available on this device.");
            case StandardIntegrityErrorCode.PLAY_SERVICES_VERSION_OUTDATED:
                return new CustomException("PLAY_SERVICES_VERSION_OUTDATED", "Google Play Services needs to be updated.");
            case StandardIntegrityErrorCode.PLAY_STORE_NOT_FOUND:
                return new CustomException("PLAY_STORE_NOT_FOUND", "No official Play Store app was found on the device.");
            case StandardIntegrityErrorCode.PLAY_STORE_VERSION_OUTDATED:
                return new CustomException("PLAY_STORE_VERSION_OUTDATED", "The Play Store app needs to be updated.");
            case StandardIntegrityErrorCode.REQUEST_HASH_TOO_LONG:
                return new CustomException(null, "requestHash must not be longer than 500 bytes.");
            case StandardIntegrityErrorCode.TOO_MANY_REQUESTS:
                return new CustomException("TOO_MANY_REQUESTS", "The calling app is making too many requests and has been throttled.");
            default:
                return exception;
        }
    }

    private void requestClassicIntegrityToken(
        @NonNull String nonce,
        @Nullable Long cloudProjectNumber,
        @NonNull NonEmptyResultCallback<RequestIntegrityTokenResult> callback
    ) {
        IntegrityTokenRequest.Builder requestBuilder = IntegrityTokenRequest.builder().setNonce(nonce);
        if (cloudProjectNumber != null) {
            requestBuilder.setCloudProjectNumber(cloudProjectNumber);
        }
        IntegrityManagerFactory.create(plugin.getContext())
            .requestIntegrityToken(requestBuilder.build())
            .addOnSuccessListener(response -> callback.success(new RequestIntegrityTokenResult(response.token())))
            .addOnFailureListener(exception -> callback.error(mapException(exception)));
    }

    private void requestStandardIntegrityToken(
        @NonNull String requestHash,
        @NonNull NonEmptyResultCallback<RequestIntegrityTokenResult> callback
    ) throws Exception {
        StandardIntegrityManager.StandardIntegrityTokenProvider provider = standardIntegrityTokenProvider;
        if (provider == null) {
            throw CustomExceptions.INTEGRITY_TOKEN_PROVIDER_NOT_PREPARED;
        }
        StandardIntegrityManager.StandardIntegrityTokenRequest request = StandardIntegrityManager.StandardIntegrityTokenRequest.builder()
            .setRequestHash(requestHash)
            .build();
        provider
            .request(request)
            .addOnSuccessListener(token -> callback.success(new RequestIntegrityTokenResult(token.token())))
            .addOnFailureListener(exception -> callback.error(mapException(exception)));
    }
}
