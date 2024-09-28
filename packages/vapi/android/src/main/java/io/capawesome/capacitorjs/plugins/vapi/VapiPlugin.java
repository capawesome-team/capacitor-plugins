package io.capawesome.capacitorjs.plugins.vapi;

import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import io.capawesome.capacitorjs.plugins.vapi.classes.options.SetupOptions;

@CapacitorPlugin(name = "Vapi")
public class VapiPlugin extends Plugin {

    public static final String TAG = "Vapi";

    private static final String ERROR_API_KEY_MISSING = "apiKey must be provided.";
    private static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";

    private VapiImpl implementation;

    @Override
    public void load() {
        try {
            implementation = new VapiImpl(this);
        } catch (Exception exception) {
            Logger.error(VapiPlugin.TAG, exception.getMessage(), exception);
        }
    }

    @PluginMethod
    public void setup(PluginCall call) {
        try {
            String apiKey = call.getString("apiKey");
            if (apiKey == null) {
                call.reject(ERROR_API_KEY_MISSING);
                return;
            }

            SetupOptions options = new SetupOptions(apiKey);

            implementation.setup(options);
            call.resolve();
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
