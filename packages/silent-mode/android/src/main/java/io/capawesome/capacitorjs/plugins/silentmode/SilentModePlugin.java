package io.capawesome.capacitorjs.plugins.silentmode;

import androidx.annotation.NonNull;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.silentmode.classes.events.SilentModeChangeEvent;
import io.capawesome.capacitorjs.plugins.silentmode.classes.results.GetRingerModeResult;
import io.capawesome.capacitorjs.plugins.silentmode.classes.results.IsSilentResult;
import io.capawesome.capacitorjs.plugins.silentmode.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.silentmode.interfaces.Result;

@CapacitorPlugin(name = "SilentMode")
public class SilentModePlugin extends Plugin {

    public static final String EVENT_SILENT_MODE_CHANGE = "silentModeChange";

    public static final String TAG = "SilentModePlugin";

    private static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private SilentMode implementation;

    @Override
    public void load() {
        implementation = new SilentMode(this);
    }

    @Override
    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void addListener(PluginCall call) {
        super.addListener(call);
        implementation.startObserving();
    }

    @PluginMethod
    public void getRingerMode(PluginCall call) {
        try {
            NonEmptyResultCallback<GetRingerModeResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetRingerModeResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getRingerMode(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isSilent(PluginCall call) {
        try {
            NonEmptyResultCallback<IsSilentResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsSilentResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.isSilent(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void notifySilentModeChangeListeners(@NonNull SilentModeChangeEvent event) {
        notifyListeners(EVENT_SILENT_MODE_CHANGE, event.toJSObject());
    }

    @Override
    @PluginMethod
    public void removeAllListeners(PluginCall call) {
        super.removeAllListeners(call);
        implementation.stopObserving();
    }

    @Override
    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void removeListener(PluginCall call) {
        super.removeListener(call);
        if (!hasListeners(EVENT_SILENT_MODE_CHANGE)) {
            implementation.stopObserving();
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

    private void resolveCall(@NonNull PluginCall call, @NonNull Result result) {
        call.resolve(result.toJSObject());
    }
}
