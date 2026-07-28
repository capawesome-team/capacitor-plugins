package io.capawesome.capacitorjs.plugins.agesignals;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.agesignals.classes.options.SetNextAgeSignalsAccessResultOptions;
import io.capawesome.capacitorjs.plugins.agesignals.classes.options.SetNextAgeSignalsExceptionOptions;
import io.capawesome.capacitorjs.plugins.agesignals.classes.options.SetNextAgeSignalsResultOptions;
import io.capawesome.capacitorjs.plugins.agesignals.classes.options.SetUseFakeManagerOptions;
import io.capawesome.capacitorjs.plugins.agesignals.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.agesignals.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.agesignals.interfaces.Result;

@CapacitorPlugin(name = "AgeSignals")
public class AgeSignalsPlugin extends Plugin {

    public static final String TAG = "AgeSignals";
    private static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private AgeSignals implementation;

    public void load() {
        try {
            implementation = new AgeSignals(this);
        } catch (Exception exception) {
            Logger.error(TAG, "Failed to load AgeSignals plugin.", exception);
        }
    }

    @PluginMethod
    public void getAgeRange(PluginCall call) {
        try {
            assert implementation != null;
            implementation.getAgeRange(createResultCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getRegulatoryRequirements(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void isAvailable(PluginCall call) {
        try {
            assert implementation != null;
            implementation.isAvailable(createResultCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void requestAgeRange(PluginCall call) {
        try {
            assert implementation != null;
            implementation.requestAgeRange(createResultCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setNextAgeSignalsAccessResult(PluginCall call) {
        try {
            SetNextAgeSignalsAccessResultOptions options = new SetNextAgeSignalsAccessResultOptions(call);

            assert implementation != null;
            implementation.setNextAgeSignalsAccessResult(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setNextAgeSignalsException(PluginCall call) {
        try {
            SetNextAgeSignalsExceptionOptions options = new SetNextAgeSignalsExceptionOptions(call);

            assert implementation != null;
            implementation.setNextAgeSignalsException(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setNextAgeSignalsResult(PluginCall call) {
        try {
            SetNextAgeSignalsResultOptions options = new SetNextAgeSignalsResultOptions(call);

            assert implementation != null;
            implementation.setNextAgeSignalsResult(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setNextRequestAgeSignalsAccessException(PluginCall call) {
        try {
            SetNextAgeSignalsExceptionOptions options = new SetNextAgeSignalsExceptionOptions(call);

            assert implementation != null;
            implementation.setNextRequestAgeSignalsAccessException(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setUseFakeManager(PluginCall call) {
        try {
            SetUseFakeManagerOptions options = new SetUseFakeManagerOptions(call);

            assert implementation != null;
            implementation.setUseFakeManager(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void showSignificantUpdateAcknowledgment(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @NonNull
    private EmptyCallback createEmptyCallback(@NonNull PluginCall call) {
        return new EmptyCallback() {
            @Override
            public void success() {
                resolveCall(call);
            }

            @Override
            public void error(@NonNull Exception exception) {
                rejectCall(call, exception);
            }
        };
    }

    @NonNull
    private <T extends Result> NonEmptyResultCallback<T> createResultCallback(@NonNull PluginCall call) {
        return new NonEmptyResultCallback<T>() {
            @Override
            public void success(@NonNull T result) {
                resolveCall(call, result);
            }

            @Override
            public void error(@NonNull Exception exception) {
                rejectCall(call, exception);
            }
        };
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        Logger.error(TAG, message, exception);
        call.reject(message);
    }

    private void rejectCallAsUnimplemented(@NonNull PluginCall call) {
        call.unimplemented("This method is not available on this platform.");
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
