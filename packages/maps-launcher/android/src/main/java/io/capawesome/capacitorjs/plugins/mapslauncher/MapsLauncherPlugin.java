package io.capawesome.capacitorjs.plugins.mapslauncher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.mapslauncher.classes.CustomException;
import io.capawesome.capacitorjs.plugins.mapslauncher.classes.options.NavigateOptions;
import io.capawesome.capacitorjs.plugins.mapslauncher.classes.results.GetAvailableAppsResult;
import io.capawesome.capacitorjs.plugins.mapslauncher.classes.results.GetDefaultAppResult;
import io.capawesome.capacitorjs.plugins.mapslauncher.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.mapslauncher.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.mapslauncher.interfaces.Result;

@CapacitorPlugin(name = "MapsLauncher")
public class MapsLauncherPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "MapsLauncherPlugin";

    private MapsLauncher implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new MapsLauncher(this);
    }

    @PluginMethod
    public void getAvailableApps(PluginCall call) {
        try {
            NonEmptyResultCallback<GetAvailableAppsResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetAvailableAppsResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getAvailableApps(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getDefaultApp(PluginCall call) {
        try {
            NonEmptyResultCallback<GetDefaultAppResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetDefaultAppResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getDefaultApp(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void navigate(PluginCall call) {
        try {
            NavigateOptions options = new NavigateOptions(call);
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
            implementation.navigate(options, callback);
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
