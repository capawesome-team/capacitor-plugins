package io.capawesome.capacitorjs.plugins.localization;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.localization.classes.results.GetLocalesResult;
import io.capawesome.capacitorjs.plugins.localization.classes.results.GetSettingsResult;
import io.capawesome.capacitorjs.plugins.localization.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.localization.interfaces.Result;

@CapacitorPlugin(name = "Localization")
public class LocalizationPlugin extends Plugin {

    public static final String TAG = "Localization";

    private static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private Localization implementation;

    @Override
    public void load() {
        implementation = new Localization(getContext());
    }

    @PluginMethod
    public void getLocales(PluginCall call) {
        try {
            NonEmptyResultCallback<GetLocalesResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetLocalesResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getLocales(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getSettings(PluginCall call) {
        try {
            NonEmptyResultCallback<GetSettingsResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetSettingsResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getSettings(callback);
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
