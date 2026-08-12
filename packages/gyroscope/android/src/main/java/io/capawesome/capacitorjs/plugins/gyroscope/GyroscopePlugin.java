package io.capawesome.capacitorjs.plugins.gyroscope;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.gyroscope.classes.Measurement;
import io.capawesome.capacitorjs.plugins.gyroscope.classes.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.gyroscope.classes.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.gyroscope.classes.interfaces.Result;
import io.capawesome.capacitorjs.plugins.gyroscope.classes.results.GetMeasurementResult;
import io.capawesome.capacitorjs.plugins.gyroscope.classes.results.IsAvailableResult;

@CapacitorPlugin(name = "Gyroscope")
public class GyroscopePlugin extends Plugin {

    public static final String ERROR_GYROSCOPE_UNAVAILABLE = "Not available on this device.";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String EVENT_MEASUREMENT = "measurement";
    public static final String TAG = "GyroscopePlugin";

    private final Gyroscope implementation = new Gyroscope(this);

    @PluginMethod
    public void checkPermissions(PluginCall call) {
        JSObject result = new JSObject();
        result.put("gyroscope", "granted");
        call.resolve(result);
    }

    @PluginMethod
    public void getMeasurement(PluginCall call) {
        try {
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
            NonEmptyResultCallback<IsAvailableResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsAvailableResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.isAvailable(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void notifyMeasurementEventListeners(@NonNull Measurement measurement) {
        notifyListeners(GyroscopePlugin.EVENT_MEASUREMENT, measurement.toJSObject());
    }

    @PluginMethod
    public void requestPermissions(PluginCall call) {
        JSObject result = new JSObject();
        result.put("gyroscope", "granted");
        call.resolve(result);
    }

    @PluginMethod
    public void startMeasurementUpdates(PluginCall call) {
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

    @PluginMethod
    public void removeAllListeners(PluginCall call) {
        super.removeAllListeners(call);
        implementation.stopMeasurementUpdates(null);
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        Logger.error(TAG, message, exception);
        call.reject(message);
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
