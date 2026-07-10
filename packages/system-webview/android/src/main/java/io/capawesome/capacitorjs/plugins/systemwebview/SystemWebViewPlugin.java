package io.capawesome.capacitorjs.plugins.systemwebview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.systemwebview.classes.CustomException;
import io.capawesome.capacitorjs.plugins.systemwebview.classes.options.IsUpdateRequiredOptions;
import io.capawesome.capacitorjs.plugins.systemwebview.classes.results.GetInfoResult;
import io.capawesome.capacitorjs.plugins.systemwebview.classes.results.IsUpdateRequiredResult;
import io.capawesome.capacitorjs.plugins.systemwebview.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.systemwebview.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.systemwebview.interfaces.Result;

@CapacitorPlugin(name = "SystemWebView")
public class SystemWebViewPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";
    public static final String TAG = "SystemWebViewPlugin";

    private SystemWebView implementation;

    @Override
    public void load() {
        implementation = new SystemWebView(this);
    }

    @PluginMethod
    public void getInfo(PluginCall call) {
        try {
            NonEmptyResultCallback<GetInfoResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetInfoResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getInfo(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isUpdateRequired(PluginCall call) {
        try {
            IsUpdateRequiredOptions options = new IsUpdateRequiredOptions(call);
            NonEmptyResultCallback<IsUpdateRequiredResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsUpdateRequiredResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.isUpdateRequired(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void openAppStore(PluginCall call) {
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
            implementation.openAppStore(callback);
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
