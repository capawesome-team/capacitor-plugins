package io.capawesome.capacitorjs.plugins.googlesignin;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.android.gms.auth.api.identity.AuthorizationResult;
import com.google.android.gms.auth.api.identity.Identity;
import io.capawesome.capacitorjs.plugins.googlesignin.classes.CustomException;
import io.capawesome.capacitorjs.plugins.googlesignin.classes.options.InitializeOptions;
import io.capawesome.capacitorjs.plugins.googlesignin.classes.options.SignInOptions;
import io.capawesome.capacitorjs.plugins.googlesignin.classes.results.SignInResult;
import io.capawesome.capacitorjs.plugins.googlesignin.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.googlesignin.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.googlesignin.interfaces.Result;

@CapacitorPlugin(name = "GoogleSignIn")
public class GoogleSignInPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "GoogleSignInPlugin";

    private GoogleSignIn implementation;
    private ActivityResultLauncher<IntentSenderRequest> authorizationLauncher;

    @Override
    public void load() {
        super.load();
        this.implementation = new GoogleSignIn(this);
        this.authorizationLauncher = getActivity()
            .registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), this::handleAuthorizationActivityResult);
    }

    @PluginMethod
    public void initialize(PluginCall call) {
        try {
            InitializeOptions options = new InitializeOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.initialize(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void signIn(PluginCall call) {
        try {
            SignInOptions options = new SignInOptions(call);
            NonEmptyResultCallback<SignInResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull SignInResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.signIn(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void handleRedirectCallback(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void signOut(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.signOut(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void launchAuthorizationIntent(@NonNull PendingIntent pendingIntent) {
        IntentSenderRequest request = new IntentSenderRequest.Builder(pendingIntent).build();
        authorizationLauncher.launch(request);
    }

    private void handleAuthorizationActivityResult(@NonNull ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
            try {
                AuthorizationResult authResult = Identity.getAuthorizationClient(getActivity()).getAuthorizationResultFromIntent(
                    result.getData()
                );
                implementation.handleAuthorizationResult(authResult);
            } catch (Exception e) {
                implementation.handleAuthorizationCanceled();
            }
        } else {
            implementation.handleAuthorizationCanceled();
        }
    }

    private void rejectCallAsUnimplemented(@NonNull PluginCall call) {
        call.unimplemented("This method is not available on this platform.");
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        String code = null;
        if (exception instanceof CustomException) {
            code = ((CustomException) exception).getCode();
        }
        Logger.error(TAG, message, exception);
        call.reject(message, code);
    }

    private void resolveCall(@NonNull PluginCall call) {
        call.resolve();
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
