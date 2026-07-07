package io.capawesome.capacitorjs.plugins.facebooksignin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.facebooksignin.classes.CustomException;
import io.capawesome.capacitorjs.plugins.facebooksignin.classes.options.InitializeOptions;
import io.capawesome.capacitorjs.plugins.facebooksignin.classes.options.SignInOptions;
import io.capawesome.capacitorjs.plugins.facebooksignin.classes.results.GetCurrentAccessTokenResult;
import io.capawesome.capacitorjs.plugins.facebooksignin.classes.results.SignInResult;
import io.capawesome.capacitorjs.plugins.facebooksignin.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.facebooksignin.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.facebooksignin.interfaces.Result;

@CapacitorPlugin(name = "FacebookSignIn")
public class FacebookSignInPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "FacebookSignInPlugin";

    private FacebookSignIn implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new FacebookSignIn(this);
    }

    @PluginMethod
    public void getCurrentAccessToken(PluginCall call) {
        try {
            NonEmptyResultCallback<GetCurrentAccessTokenResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetCurrentAccessTokenResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getCurrentAccessToken(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
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
