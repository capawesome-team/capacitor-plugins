package io.capawesome.capacitorjs.plugins.appintegrity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.appintegrity.classes.CustomException;
import io.capawesome.capacitorjs.plugins.appintegrity.classes.options.PrepareIntegrityTokenOptions;
import io.capawesome.capacitorjs.plugins.appintegrity.classes.options.RequestIntegrityTokenOptions;
import io.capawesome.capacitorjs.plugins.appintegrity.classes.results.IsAvailableResult;
import io.capawesome.capacitorjs.plugins.appintegrity.classes.results.RequestIntegrityTokenResult;
import io.capawesome.capacitorjs.plugins.appintegrity.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.appintegrity.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.appintegrity.interfaces.Result;

@CapacitorPlugin(name = "AppIntegrity")
public class AppIntegrityPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "AppIntegrity";

    private AppIntegrity implementation;

    @Override
    public void load() {
        this.implementation = new AppIntegrity(this);
    }

    @PluginMethod
    public void attestKey(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void generateAssertion(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void generateKey(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void isAvailable(PluginCall call) {
        try {
            NonEmptyResultCallback<IsAvailableResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsAvailableResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.isAvailable(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void prepareIntegrityToken(PluginCall call) {
        try {
            PrepareIntegrityTokenOptions options = new PrepareIntegrityTokenOptions(call);
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
            implementation.prepareIntegrityToken(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void requestIntegrityToken(PluginCall call) {
        try {
            RequestIntegrityTokenOptions options = new RequestIntegrityTokenOptions(call);
            NonEmptyResultCallback<RequestIntegrityTokenResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull RequestIntegrityTokenResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.requestIntegrityToken(options, callback);
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

    private void rejectCallAsUnimplemented(@NonNull PluginCall call) {
        call.unimplemented("This method is not available on this platform.");
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
