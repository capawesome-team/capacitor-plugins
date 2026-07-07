package io.capawesome.capacitorjs.plugins.proximitysensor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.proximitysensor.classes.Measurement;
import io.capawesome.capacitorjs.plugins.proximitysensor.classes.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.proximitysensor.classes.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.proximitysensor.classes.interfaces.Result;
import io.capawesome.capacitorjs.plugins.proximitysensor.classes.results.GetMeasurementResult;
import io.capawesome.capacitorjs.plugins.proximitysensor.classes.results.IsAvailableResult;

@CapacitorPlugin(name = "ProximitySensor")
public class ProximitySensorPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String EVENT_MEASUREMENT = "measurement";
    public static final String TAG = "ProximitySensorPlugin";

    private final ProximitySensor implementation = new ProximitySensor(this);

    @PluginMethod
    public void getMeasurement(PluginCall call) {
        try {
            if (!implementation.isAvailable()) {
                rejectCallAsUnavailable(call);
                return;
            }

            NonEmptyResultCallback<GetMeasurementResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetMeasurementResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getMeasurement(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isAvailable(PluginCall call) {
        try {
            IsAvailableResult result = new IsAvailableResult(implementation.isAvailable());
            resolveCall(call, result);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void notifyMeasurementEventListeners(@NonNull Measurement measurement) {
        notifyListeners(ProximitySensorPlugin.EVENT_MEASUREMENT, measurement.toJSObject());
    }

    @PluginMethod
    public void removeAllListeners(PluginCall call) {
        super.removeAllListeners(call);
        implementation.stopMeasurementUpdates(null);
    }

    @PluginMethod
    public void startMeasurementUpdates(PluginCall call) {
        try {
            if (!implementation.isAvailable()) {
                rejectCallAsUnavailable(call);
                return;
            }

            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call, null);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.startMeasurementUpdates(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void stopMeasurementUpdates(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call, null);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.stopMeasurementUpdates(callback);
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

    private void rejectCallAsUnavailable(@NonNull PluginCall call) {
        call.unavailable("This method is not available on this platform.");
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
