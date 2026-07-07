package io.capawesome.capacitorjs.plugins.appicon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.appicon.classes.CustomException;
import io.capawesome.capacitorjs.plugins.appicon.classes.options.SetIconOptions;
import io.capawesome.capacitorjs.plugins.appicon.classes.results.GetCurrentIconResult;
import io.capawesome.capacitorjs.plugins.appicon.classes.results.IsAvailableResult;
import io.capawesome.capacitorjs.plugins.appicon.interfaces.Result;

@CapacitorPlugin(name = "AppIcon")
public class AppIconPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";
    public static final String TAG = "AppIcon";

    private AppIcon implementation;

    @Override
    public void load() {
        implementation = new AppIcon(this);
    }

    @PluginMethod
    public void getCurrentIcon(PluginCall call) {
        try {
            GetCurrentIconResult result = implementation.getCurrentIcon();
            resolveCall(call, result);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isAvailable(PluginCall call) {
        try {
            IsAvailableResult result = implementation.isAvailable();
            resolveCall(call, result);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void resetIcon(PluginCall call) {
        try {
            implementation.resetIcon();
            resolveCall(call);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setIcon(PluginCall call) {
        try {
            SetIconOptions options = new SetIconOptions(call);
            implementation.setIcon(options);
            resolveCall(call);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        String code = null;
        if (exception instanceof CustomException) {
            code = ((CustomException) exception).getCode();
        }
        Logger.error(TAG, message, exception);
        call.reject(message, code);
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
