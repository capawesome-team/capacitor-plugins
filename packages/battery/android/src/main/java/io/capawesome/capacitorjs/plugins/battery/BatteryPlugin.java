package io.capawesome.capacitorjs.plugins.battery;

import androidx.annotation.NonNull;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.battery.classes.events.BatteryLevelChangeEvent;
import io.capawesome.capacitorjs.plugins.battery.classes.events.BatteryStateChangeEvent;
import io.capawesome.capacitorjs.plugins.battery.classes.events.LowPowerModeChangeEvent;
import io.capawesome.capacitorjs.plugins.battery.classes.results.GetBatteryLevelResult;
import io.capawesome.capacitorjs.plugins.battery.classes.results.GetBatteryStateResult;
import io.capawesome.capacitorjs.plugins.battery.classes.results.IsLowPowerModeEnabledResult;
import io.capawesome.capacitorjs.plugins.battery.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.battery.interfaces.Result;

@CapacitorPlugin(name = "Battery")
public class BatteryPlugin extends Plugin {

    public static final String EVENT_BATTERY_LEVEL_CHANGE = "batteryLevelChange";

    public static final String EVENT_BATTERY_STATE_CHANGE = "batteryStateChange";

    public static final String EVENT_LOW_POWER_MODE_CHANGE = "lowPowerModeChange";

    public static final String TAG = "BatteryPlugin";

    private static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private Battery implementation;

    @Override
    public void load() {
        implementation = new Battery(this);
    }

    @Override
    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void addListener(PluginCall call) {
        super.addListener(call);
        implementation.startObserving();
    }

    @PluginMethod
    public void getBatteryLevel(PluginCall call) {
        try {
            NonEmptyResultCallback<GetBatteryLevelResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetBatteryLevelResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getBatteryLevel(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getBatteryState(PluginCall call) {
        try {
            NonEmptyResultCallback<GetBatteryStateResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetBatteryStateResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getBatteryState(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isLowPowerModeEnabled(PluginCall call) {
        try {
            NonEmptyResultCallback<IsLowPowerModeEnabledResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsLowPowerModeEnabledResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.isLowPowerModeEnabled(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void notifyBatteryLevelChangeListeners(@NonNull BatteryLevelChangeEvent event) {
        notifyListeners(EVENT_BATTERY_LEVEL_CHANGE, event.toJSObject());
    }

    public void notifyBatteryStateChangeListeners(@NonNull BatteryStateChangeEvent event) {
        notifyListeners(EVENT_BATTERY_STATE_CHANGE, event.toJSObject());
    }

    public void notifyLowPowerModeChangeListeners(@NonNull LowPowerModeChangeEvent event) {
        notifyListeners(EVENT_LOW_POWER_MODE_CHANGE, event.toJSObject());
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
        if (!hasAnyListeners()) {
            implementation.stopObserving();
        }
    }

    private boolean hasAnyListeners() {
        return (
            hasListeners(EVENT_BATTERY_LEVEL_CHANGE) ||
            hasListeners(EVENT_BATTERY_STATE_CHANGE) ||
            hasListeners(EVENT_LOW_POWER_MODE_CHANGE)
        );
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
