package io.capawesome.capacitorjs.plugins.agesignals;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.agesignals.classes.results.CheckAgeSignalsResult;
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
    public void checkAgeSignals(PluginCall call) {
        try {
            NonEmptyResultCallback<CheckAgeSignalsResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull CheckAgeSignalsResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.checkAgeSignals(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void checkEligibility(PluginCall call) {
        rejectCallAsUnimplemented(call);
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

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
