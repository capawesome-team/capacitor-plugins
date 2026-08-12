package io.capawesome.capacitorjs.plugins.accessibilitypreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.accessibilitypreferences.classes.results.GetPreferencesResult;
import io.capawesome.capacitorjs.plugins.accessibilitypreferences.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.accessibilitypreferences.interfaces.Result;

@CapacitorPlugin(name = "AccessibilityPreferences")
public class AccessibilityPreferencesPlugin extends Plugin {

    public static final String TAG = "AccessibilityPreferences";

    private static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private AccessibilityPreferences implementation;

    @Override
    public void load() {
        implementation = new AccessibilityPreferences(getContext());
    }

    @PluginMethod
    public void getPreferences(PluginCall call) {
        try {
            NonEmptyResultCallback<GetPreferencesResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetPreferencesResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getPreferences(callback);
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

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
