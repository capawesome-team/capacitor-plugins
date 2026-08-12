package io.capawesome.capacitorjs.plugins.screenbrightness;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.screenbrightness.classes.options.SetBrightnessOptions;
import io.capawesome.capacitorjs.plugins.screenbrightness.classes.results.GetBrightnessResult;
import io.capawesome.capacitorjs.plugins.screenbrightness.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.screenbrightness.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.screenbrightness.interfaces.Result;

@CapacitorPlugin(name = "ScreenBrightness")
public class ScreenBrightnessPlugin extends Plugin {

    public static final String TAG = "ScreenBrightness";

    private static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private ScreenBrightness implementation;

    @Override
    public void load() {
        implementation = new ScreenBrightness(this);
    }

    @PluginMethod
    public void getBrightness(PluginCall call) {
        try {
            NonEmptyResultCallback<GetBrightnessResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetBrightnessResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getBrightness(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void resetBrightness(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.resetBrightness(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setBrightness(PluginCall call) {
        try {
            SetBrightnessOptions options = new SetBrightnessOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.setBrightness(options, callback);
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
