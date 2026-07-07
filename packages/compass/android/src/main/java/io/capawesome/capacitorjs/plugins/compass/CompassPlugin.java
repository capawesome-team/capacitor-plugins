package io.capawesome.capacitorjs.plugins.compass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.compass.classes.events.HeadingChangeEvent;
import io.capawesome.capacitorjs.plugins.compass.classes.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.compass.classes.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.compass.classes.interfaces.Result;
import io.capawesome.capacitorjs.plugins.compass.classes.results.GetHeadingResult;
import io.capawesome.capacitorjs.plugins.compass.classes.results.IsAvailableResult;

@CapacitorPlugin(name = "Compass")
public class CompassPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String EVENT_HEADING_CHANGE = "headingChange";
    public static final String TAG = "CompassPlugin";

    private final Compass implementation = new Compass(this);

    @PluginMethod
    public void getHeading(PluginCall call) {
        try {
            if (!implementation.isAvailable()) {
                rejectCallAsUnavailable(call);
                return;
            }

            NonEmptyResultCallback<GetHeadingResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetHeadingResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getHeading(callback);
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

    public void notifyHeadingChangeListeners(@NonNull HeadingChangeEvent event) {
        notifyListeners(EVENT_HEADING_CHANGE, event.toJSObject());
    }

    @PluginMethod
    public void removeAllListeners(PluginCall call) {
        super.removeAllListeners(call);
        implementation.stopHeadingUpdates(null);
    }

    @PluginMethod
    public void startHeadingUpdates(PluginCall call) {
        try {
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
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.startHeadingUpdates(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void stopHeadingUpdates(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.stopHeadingUpdates(callback);
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

    private void resolveCall(@NonNull PluginCall call) {
        call.resolve();
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
