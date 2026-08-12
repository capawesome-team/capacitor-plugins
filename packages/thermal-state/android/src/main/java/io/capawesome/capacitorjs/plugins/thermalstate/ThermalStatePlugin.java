package io.capawesome.capacitorjs.plugins.thermalstate;

import android.os.Build;
import androidx.annotation.NonNull;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.thermalstate.classes.events.ThermalStateChangeEvent;
import io.capawesome.capacitorjs.plugins.thermalstate.classes.results.GetThermalStateResult;
import io.capawesome.capacitorjs.plugins.thermalstate.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.thermalstate.interfaces.Result;

@CapacitorPlugin(name = "ThermalState")
public class ThermalStatePlugin extends Plugin {

    public static final String EVENT_THERMAL_STATE_CHANGE = "thermalStateChange";

    public static final String TAG = "ThermalStatePlugin";

    private static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private ThermalState implementation;

    @Override
    public void load() {
        implementation = new ThermalState(this);
    }

    @Override
    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void addListener(PluginCall call) {
        super.addListener(call);
        implementation.startObserving();
    }

    @PluginMethod
    public void getThermalState(PluginCall call) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                rejectCallAsUnavailable(call);
                return;
            }
            NonEmptyResultCallback<GetThermalStateResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetThermalStateResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getThermalState(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void notifyThermalStateChangeListeners(@NonNull ThermalStateChangeEvent event) {
        notifyListeners(EVENT_THERMAL_STATE_CHANGE, event.toJSObject());
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
        if (!hasListeners(EVENT_THERMAL_STATE_CHANGE)) {
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

    private void rejectCallAsUnavailable(@NonNull PluginCall call) {
        call.unavailable("This method is not available on this platform.");
    }

    private void resolveCall(@NonNull PluginCall call, @NonNull Result result) {
        call.resolve(result.toJSObject());
    }
}
