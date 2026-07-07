package io.capawesome.capacitorjs.plugins.passwordautofill;

import androidx.annotation.NonNull;
import androidx.credentials.CreateCredentialResponse;
import androidx.credentials.CreatePasswordRequest;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.exceptions.CreateCredentialCancellationException;
import androidx.credentials.exceptions.CreateCredentialException;
import io.capawesome.capacitorjs.plugins.passwordautofill.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.passwordautofill.classes.options.SavePasswordOptions;
import io.capawesome.capacitorjs.plugins.passwordautofill.interfaces.EmptyCallback;

public class PasswordAutofill {

    @NonNull
    private final PasswordAutofillPlugin plugin;

    public PasswordAutofill(@NonNull PasswordAutofillPlugin plugin) {
        this.plugin = plugin;
    }

    public void savePassword(@NonNull SavePasswordOptions options, @NonNull EmptyCallback callback) {
        CreatePasswordRequest request = new CreatePasswordRequest(options.getUsername(), options.getPassword());
        CredentialManager credentialManager = CredentialManager.create(plugin.getContext());
        credentialManager.createCredentialAsync(
            plugin.getActivity(),
            request,
            null,
            Runnable::run,
            new CredentialManagerCallback<CreateCredentialResponse, CreateCredentialException>() {
                @Override
                public void onResult(@NonNull CreateCredentialResponse response) {
                    callback.success();
                }

                @Override
                public void onError(@NonNull CreateCredentialException exception) {
                    if (exception instanceof CreateCredentialCancellationException) {
                        callback.error(CustomExceptions.CANCELED);
                    } else {
                        String message = exception.getMessage();
                        callback.error(CustomExceptions.saveFailed(message == null ? "The credential could not be saved." : message));
                    }
                }
            }
        );
    }
}
