package io.capawesome.capacitorjs.plugins.privacyscreen;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.privacyscreen.classes.results.IsEnabledResult;
import io.capawesome.capacitorjs.plugins.privacyscreen.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.privacyscreen.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.privacyscreen.interfaces.Result;

@CapacitorPlugin(name = "PrivacyScreen")
public class PrivacyScreenPlugin extends Plugin {

    public static final String EVENT_SCREENSHOT_TAKEN = "screenshotTaken";

    public static final String TAG = "PrivacyScreenPlugin";

    private static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private PrivacyScreen implementation;

    @Override
    public void load() {
        implementation = new PrivacyScreen(this);
    }

    @Override
    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void addListener(PluginCall call) {
        super.addListener(call);
        implementation.startObserving();
    }

    @PluginMethod
    public void disable(PluginCall call) {
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

            implementation.disable(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void enable(PluginCall call) {
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

            implementation.enable(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isEnabled(PluginCall call) {
        try {
            NonEmptyResultCallback<IsEnabledResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsEnabledResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.isEnabled(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void notifyScreenshotTakenListeners() {
        notifyListeners(EVENT_SCREENSHOT_TAKEN, new JSObject());
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
        if (!hasListeners(EVENT_SCREENSHOT_TAKEN)) {
            implementation.stopObserving();
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

    private void resolveCall(@NonNull PluginCall call, @NonNull Result result) {
        call.resolve(result.toJSObject());
    }
}
