package io.capawesome.capacitorjs.plugins.keepawake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.keepawake.classes.results.IsAvailableResult;
import io.capawesome.capacitorjs.plugins.keepawake.classes.results.IsKeptAwakeResult;
import io.capawesome.capacitorjs.plugins.keepawake.interfaces.Result;

@CapacitorPlugin(name = "KeepAwake")
public class KeepAwakePlugin extends Plugin {

    public static final String TAG = "KeepAwake";

    private static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private KeepAwake implementation;

    @Override
    public void load() {
        implementation = new KeepAwake(this);
    }

    @PluginMethod
    public void allowSleep(PluginCall call) {
        try {
            implementation.allowSleep();
            resolveCall(call);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isAvailable(PluginCall call) {
        try {
            IsAvailableResult result = implementation.isAvailable();
            resolveCall(call, result);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isKeptAwake(PluginCall call) {
        try {
            IsKeptAwakeResult result = implementation.isKeptAwake();
            resolveCall(call, result);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void keepAwake(PluginCall call) {
        try {
            implementation.keepAwake();
            resolveCall(call);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        message = (message != null) ? message : ERROR_UNKNOWN_ERROR;
        Logger.error(TAG, message, exception);
        call.reject(message);
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
