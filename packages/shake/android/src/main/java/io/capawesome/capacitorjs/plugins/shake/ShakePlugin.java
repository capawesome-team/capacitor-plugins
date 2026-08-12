package io.capawesome.capacitorjs.plugins.shake;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.shake.classes.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.shake.classes.options.StartWatchingOptions;

@CapacitorPlugin(name = "Shake")
public class ShakePlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";
    public static final String EVENT_SHAKE = "shake";
    public static final String TAG = "ShakePlugin";

    private Shake implementation;

    @Override
    public void load() {
        implementation = new Shake(this);
    }

    public void notifyShakeListeners() {
        notifyListeners(EVENT_SHAKE, new JSObject());
    }

    @PluginMethod
    public void startWatching(PluginCall call) {
        try {
            StartWatchingOptions options = new StartWatchingOptions(call);
            if (!implementation.isAvailable()) {
                rejectCallAsUnavailable(call);
                return;
            }
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.startWatching(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void stopWatching(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.stopWatching(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @Override
    protected void handleOnDestroy() {
        implementation.stopWatching(null);
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        Logger.error(TAG, message, exception);
        call.reject(message);
    }

    private void rejectCallAsUnavailable(@NonNull PluginCall call) {
        call.unavailable("This method is not available on this platform.");
    }

    private void resolveCall(@NonNull PluginCall call) {
        call.resolve();
    }
}
