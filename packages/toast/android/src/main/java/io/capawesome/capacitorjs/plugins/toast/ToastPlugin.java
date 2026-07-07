package io.capawesome.capacitorjs.plugins.toast;

import androidx.annotation.NonNull;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.toast.classes.CustomException;
import io.capawesome.capacitorjs.plugins.toast.classes.options.ShowOptions;
import io.capawesome.capacitorjs.plugins.toast.interfaces.EmptyCallback;

@CapacitorPlugin(name = "Toast")
public class ToastPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "ToastPlugin";

    private Toast implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new Toast(this);
    }

    @PluginMethod
    public void show(PluginCall call) {
        try {
            ShowOptions options = new ShowOptions(call);
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
            implementation.show(options, callback);
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
}
