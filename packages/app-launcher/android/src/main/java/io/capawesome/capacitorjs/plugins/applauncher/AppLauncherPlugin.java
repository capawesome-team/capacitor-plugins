package io.capawesome.capacitorjs.plugins.applauncher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.applauncher.classes.CustomException;
import io.capawesome.capacitorjs.plugins.applauncher.classes.options.CanOpenUrlOptions;
import io.capawesome.capacitorjs.plugins.applauncher.classes.options.OpenUrlOptions;
import io.capawesome.capacitorjs.plugins.applauncher.classes.results.CanOpenUrlResult;
import io.capawesome.capacitorjs.plugins.applauncher.classes.results.OpenUrlResult;
import io.capawesome.capacitorjs.plugins.applauncher.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.applauncher.interfaces.Result;

@CapacitorPlugin(name = "AppLauncher")
public class AppLauncherPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "AppLauncherPlugin";

    private AppLauncher implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new AppLauncher(this);
    }

    @PluginMethod
    public void canOpenUrl(PluginCall call) {
        try {
            CanOpenUrlOptions options = new CanOpenUrlOptions(call);
            NonEmptyResultCallback<CanOpenUrlResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull CanOpenUrlResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.canOpenUrl(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void openUrl(PluginCall call) {
        try {
            OpenUrlOptions options = new OpenUrlOptions(call);
            NonEmptyResultCallback<OpenUrlResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull OpenUrlResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.openUrl(options, callback);
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
