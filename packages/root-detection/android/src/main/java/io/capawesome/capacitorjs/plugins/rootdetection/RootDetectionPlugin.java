package io.capawesome.capacitorjs.plugins.rootdetection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.rootdetection.classes.results.IsDeveloperModeEnabledResult;
import io.capawesome.capacitorjs.plugins.rootdetection.classes.results.IsEmulatorResult;
import io.capawesome.capacitorjs.plugins.rootdetection.classes.results.IsRootedResult;
import io.capawesome.capacitorjs.plugins.rootdetection.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.rootdetection.interfaces.Result;

@CapacitorPlugin(name = "RootDetection")
public class RootDetectionPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "RootDetection";

    private RootDetection implementation;

    @Override
    public void load() {
        this.implementation = new RootDetection(this);
    }

    @PluginMethod
    public void isDeveloperModeEnabled(PluginCall call) {
        try {
            NonEmptyResultCallback<IsDeveloperModeEnabledResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsDeveloperModeEnabledResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.isDeveloperModeEnabled(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isEmulator(PluginCall call) {
        try {
            NonEmptyResultCallback<IsEmulatorResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsEmulatorResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.isEmulator(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isRooted(PluginCall call) {
        try {
            NonEmptyResultCallback<IsRootedResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsRootedResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.isRooted(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        Logger.error(TAG, message, exception);
        call.reject(message);
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
