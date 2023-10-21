package io.capawesome.capacitorjs.plugins.batteryoptimization;

import android.os.Build;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "BatteryOptimization")
public class BatteryOptimizationPlugin extends Plugin {

    public static final String ERROR_MESSAGE_POWER_MANAGER_UNAVAILABLE = "The power manager is not available on this device.";
    public static final String ERROR_CODE_UNAVAILABLE = "unavailable";

    private BatteryOptimization implementation;

    @Override
    public void load() {
        implementation = new BatteryOptimization(this);
    }

    @PluginMethod
    public void isBatteryOptimizationEnabled(PluginCall call) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                call.reject(ERROR_MESSAGE_POWER_MANAGER_UNAVAILABLE, ERROR_CODE_UNAVAILABLE);
                return;
            }
            JSObject ret = new JSObject();
            ret.put("enabled", implementation.isBatteryOptimizationEnabled());
            call.resolve(ret);
        } catch (Exception exception) {
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void openBatteryOptimizationSettings(PluginCall call) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                call.reject(ERROR_MESSAGE_POWER_MANAGER_UNAVAILABLE, ERROR_CODE_UNAVAILABLE);
                return;
            }
            implementation.openBatteryOptimizationSettings();
            call.resolve();
        } catch (Exception exception) {
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void requestIgnoreBatteryOptimization(PluginCall call) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                call.reject(ERROR_MESSAGE_POWER_MANAGER_UNAVAILABLE, ERROR_CODE_UNAVAILABLE);
                return;
            }
            implementation.requestIgnoreBatteryOptimization();
            call.resolve();
        } catch (Exception exception) {
            call.reject(exception.getMessage());
        }
    }
}
