package io.capawesome.capacitorjs.plugins.passkeys;

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.credentials.CreateCredentialResponse;
import androidx.credentials.CreatePublicKeyCredentialRequest;
import androidx.credentials.CreatePublicKeyCredentialResponse;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.GetPublicKeyCredentialOption;
import androidx.credentials.PublicKeyCredential;
import androidx.credentials.exceptions.CreateCredentialCancellationException;
import androidx.credentials.exceptions.CreateCredentialException;
import androidx.credentials.exceptions.CreateCredentialProviderConfigurationException;
import androidx.credentials.exceptions.CreateCredentialUnsupportedException;
import androidx.credentials.exceptions.GetCredentialCancellationException;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.credentials.exceptions.GetCredentialProviderConfigurationException;
import androidx.credentials.exceptions.GetCredentialUnsupportedException;
import androidx.credentials.exceptions.NoCredentialException;
import androidx.credentials.exceptions.domerrors.SecurityError;
import androidx.credentials.exceptions.publickeycredential.CreatePublicKeyCredentialDomException;
import androidx.credentials.exceptions.publickeycredential.GetPublicKeyCredentialDomException;
import io.capawesome.capacitorjs.plugins.passkeys.classes.CustomException;
import io.capawesome.capacitorjs.plugins.passkeys.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.passkeys.classes.options.CreatePasskeyOptions;
import io.capawesome.capacitorjs.plugins.passkeys.classes.options.GetPasskeyOptions;
import io.capawesome.capacitorjs.plugins.passkeys.classes.results.CreatePasskeyResult;
import io.capawesome.capacitorjs.plugins.passkeys.classes.results.GetPasskeyResult;
import io.capawesome.capacitorjs.plugins.passkeys.classes.results.IsAvailableResult;
import io.capawesome.capacitorjs.plugins.passkeys.interfaces.NonEmptyResultCallback;

public class Passkeys {

    @NonNull
    private final PasskeysPlugin plugin;

    public Passkeys(@NonNull PasskeysPlugin plugin) {
        this.plugin = plugin;
    }

    public void createPasskey(@NonNull CreatePasskeyOptions options, @NonNull NonEmptyResultCallback<CreatePasskeyResult> callback) {
        CreatePublicKeyCredentialRequest request = new CreatePublicKeyCredentialRequest(options.getRequestJson());
        CredentialManager credentialManager = CredentialManager.create(plugin.getContext());
        credentialManager.createCredentialAsync(
            plugin.getActivity(),
            request,
            null,
            Runnable::run,
            new CredentialManagerCallback<CreateCredentialResponse, CreateCredentialException>() {
                @Override
                public void onResult(@NonNull CreateCredentialResponse response) {
                    handleCreateCredentialResponse(response, callback);
                }

                @Override
                public void onError(@NonNull CreateCredentialException exception) {
                    callback.error(mapCreateCredentialException(exception));
                }
            }
        );
    }

    public void getPasskey(@NonNull GetPasskeyOptions options, @NonNull NonEmptyResultCallback<GetPasskeyResult> callback) {
        GetPublicKeyCredentialOption option = new GetPublicKeyCredentialOption(options.getRequestJson());
        GetCredentialRequest request = new GetCredentialRequest.Builder().addCredentialOption(option).build();
        CredentialManager credentialManager = CredentialManager.create(plugin.getContext());
        credentialManager.getCredentialAsync(
            plugin.getActivity(),
            request,
            null,
            Runnable::run,
            new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                @Override
                public void onResult(@NonNull GetCredentialResponse response) {
                    handleGetCredentialResponse(response, callback);
                }

                @Override
                public void onError(@NonNull GetCredentialException exception) {
                    callback.error(mapGetCredentialException(exception));
                }
            }
        );
    }

    public void isAvailable(@NonNull NonEmptyResultCallback<IsAvailableResult> callback) {
        boolean available = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
        IsAvailableResult result = new IsAvailableResult(available);
        callback.success(result);
    }

    private void handleCreateCredentialResponse(
        @NonNull CreateCredentialResponse response,
        @NonNull NonEmptyResultCallback<CreatePasskeyResult> callback
    ) {
        if (response instanceof CreatePublicKeyCredentialResponse) {
            try {
                String registrationResponseJson = ((CreatePublicKeyCredentialResponse) response).getRegistrationResponseJson();
                CreatePasskeyResult result = new CreatePasskeyResult(registrationResponseJson);
                callback.success(result);
            } catch (Exception exception) {
                callback.error(exception);
            }
        } else {
            callback.error(CustomExceptions.CREATE_FAILED);
        }
    }

    private void handleGetCredentialResponse(
        @NonNull GetCredentialResponse response,
        @NonNull NonEmptyResultCallback<GetPasskeyResult> callback
    ) {
        if (response.getCredential() instanceof PublicKeyCredential) {
            try {
                String authenticationResponseJson = ((PublicKeyCredential) response.getCredential()).getAuthenticationResponseJson();
                GetPasskeyResult result = new GetPasskeyResult(authenticationResponseJson);
                callback.success(result);
            } catch (Exception exception) {
                callback.error(exception);
            }
        } else {
            callback.error(CustomExceptions.GET_FAILED);
        }
    }

    private static Exception mapCreateCredentialException(@NonNull CreateCredentialException exception) {
        if (exception instanceof CreateCredentialCancellationException) {
            return CustomExceptions.OPERATION_CANCELED;
        }
        String message = exception.getMessage();
        if (message == null) {
            message = CustomExceptions.CREATE_FAILED.getMessage();
        }
        if (
            exception instanceof CreatePublicKeyCredentialDomException &&
            ((CreatePublicKeyCredentialDomException) exception).getDomError() instanceof SecurityError
        ) {
            return new CustomException("DOMAIN_NOT_ASSOCIATED", message);
        }
        if (
            exception instanceof CreateCredentialProviderConfigurationException || exception instanceof CreateCredentialUnsupportedException
        ) {
            return new CustomException("NOT_SUPPORTED", message);
        }
        return new CustomException("CREATE_FAILED", message);
    }

    private static Exception mapGetCredentialException(@NonNull GetCredentialException exception) {
        if (exception instanceof GetCredentialCancellationException) {
            return CustomExceptions.OPERATION_CANCELED;
        }
        String message = exception.getMessage();
        if (message == null) {
            message = CustomExceptions.GET_FAILED.getMessage();
        }
        if (exception instanceof NoCredentialException) {
            return new CustomException("NO_CREDENTIAL", message);
        }
        if (
            exception instanceof GetPublicKeyCredentialDomException &&
            ((GetPublicKeyCredentialDomException) exception).getDomError() instanceof SecurityError
        ) {
            return new CustomException("DOMAIN_NOT_ASSOCIATED", message);
        }
        if (exception instanceof GetCredentialProviderConfigurationException || exception instanceof GetCredentialUnsupportedException) {
            return new CustomException("NOT_SUPPORTED", message);
        }
        return new CustomException("GET_FAILED", message);
    }
}
