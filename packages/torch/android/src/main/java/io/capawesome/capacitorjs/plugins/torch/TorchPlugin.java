package io.capawesome.capacitorjs.plugins.torch;

import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "Torch")
public class TorchPlugin extends Plugin {

    public static final String TAG = "Torch";

    private static final String ERROR_NOT_AVAILABLE = "Not available on this device.";
    private static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private Torch implementation;

    @Override
    public void load() {
        try {
            implementation = new Torch(this);
        } catch (Exception exception) {
            Logger.error(TorchPlugin.TAG, exception.getMessage(), exception);
        }
    }

    @PluginMethod
    public void enable(PluginCall call) {
        try {
            boolean isAvailable = implementation.isAvailable();
            if (!isAvailable) {
                call.unavailable(ERROR_NOT_AVAILABLE);
                return;
            }

            implementation.enable();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void disable(PluginCall call) {
        try {
            boolean isAvailable = implementation.isAvailable();
            if (!isAvailable) {
                call.unavailable(ERROR_NOT_AVAILABLE);
                return;
            }

            implementation.disable();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isAvailable(PluginCall call) {
        try {
            boolean isAvailable = implementation.isAvailable();

            JSObject result = new JSObject();
            result.put("available", isAvailable);
            call.resolve(result);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isEnabled(PluginCall call) {
        try {
            boolean isEnabled = implementation.isEnabled();

            JSObject result = new JSObject();
            result.put("enabled", isEnabled);
            call.resolve(result);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void toggle(PluginCall call) {
        try {
            boolean isAvailable = implementation.isAvailable();
            if (!isAvailable) {
                call.unavailable(ERROR_NOT_AVAILABLE);
                return;
            }

            implementation.toggle();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private void rejectCall(PluginCall call, Exception exception) {
        String message = exception.getMessage();
        message = (message != null) ? message : ERROR_UNKNOWN_ERROR;
        Logger.error(TAG, message, exception);
        call.reject(message);
    }
}
