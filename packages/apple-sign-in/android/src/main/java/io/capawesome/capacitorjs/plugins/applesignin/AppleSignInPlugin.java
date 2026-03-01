package io.capawesome.capacitorjs.plugins.applesignin;

import android.content.Intent;
import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.applesignin.classes.CustomException;
import io.capawesome.capacitorjs.plugins.applesignin.classes.options.SignInOptions;
import io.capawesome.capacitorjs.plugins.applesignin.classes.results.SignInResult;
import io.capawesome.capacitorjs.plugins.applesignin.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.applesignin.interfaces.Result;

@CapacitorPlugin(name = "AppleSignIn")
public class AppleSignInPlugin extends Plugin {

    public static final String TAG = "AppleSignIn";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";

    private AppleSignIn implementation;

    @Override
    public void load() {
        this.implementation = new AppleSignIn();
    }

    @PluginMethod
    public void initialize(PluginCall call) {
        try {
            String clientId = call.getString("clientId");
            if (clientId == null) {
                call.reject("clientId must be provided.");
                return;
            }
            implementation.initialize(clientId);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void signIn(PluginCall call) {
        try {
            SignInOptions options = new SignInOptions(call);
            String url = implementation.buildAuthUrl(options);

            Intent intent = new Intent(getContext(), AppleSignInActivity.class);
            intent.putExtra(AppleSignInActivity.EXTRA_URL, url);
            intent.putExtra(AppleSignInActivity.EXTRA_REDIRECT_URL, options.getRedirectUrl());

            startActivityForResult(call, intent, "handleSignInResult");
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @ActivityCallback
    private void handleSignInResult(PluginCall call, ActivityResult activityResult) {
        if (call == null) {
            return;
        }
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
        implementation.handleActivityResult(activityResult.getResultCode(), activityResult.getData(), callback);
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
