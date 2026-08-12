package io.capawesome.capacitorjs.plugins.network;

import androidx.annotation.NonNull;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.network.classes.results.GetStatusResult;
import io.capawesome.capacitorjs.plugins.network.classes.results.IsAirplaneModeEnabledResult;
import io.capawesome.capacitorjs.plugins.network.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.network.interfaces.Result;

@CapacitorPlugin(name = "Network")
public class NetworkPlugin extends Plugin {

    public static final String EVENT_NETWORK_STATUS_CHANGE = "networkStatusChange";

    public static final String TAG = "NetworkPlugin";

    private static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private Network implementation;

    @Override
    public void load() {
        implementation = new Network(this);
    }

    @Override
    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void addListener(PluginCall call) {
        super.addListener(call);
        implementation.startObserving();
    }

    @PluginMethod
    public void getStatus(PluginCall call) {
        try {
            NonEmptyResultCallback<GetStatusResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetStatusResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getStatus(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isAirplaneModeEnabled(PluginCall call) {
        try {
            NonEmptyResultCallback<IsAirplaneModeEnabledResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsAirplaneModeEnabledResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.isAirplaneModeEnabled(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void notifyNetworkStatusChangeListeners(@NonNull GetStatusResult event) {
        notifyListeners(EVENT_NETWORK_STATUS_CHANGE, event.toJSObject());
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
        return hasListeners(EVENT_NETWORK_STATUS_CHANGE);
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
