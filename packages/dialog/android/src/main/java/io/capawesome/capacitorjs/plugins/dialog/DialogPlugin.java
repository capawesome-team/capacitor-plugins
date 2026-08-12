package io.capawesome.capacitorjs.plugins.dialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.dialog.classes.CustomException;
import io.capawesome.capacitorjs.plugins.dialog.classes.options.AlertOptions;
import io.capawesome.capacitorjs.plugins.dialog.classes.options.ConfirmOptions;
import io.capawesome.capacitorjs.plugins.dialog.classes.options.PromptOptions;
import io.capawesome.capacitorjs.plugins.dialog.classes.results.ConfirmResult;
import io.capawesome.capacitorjs.plugins.dialog.classes.results.PromptResult;
import io.capawesome.capacitorjs.plugins.dialog.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.dialog.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.dialog.interfaces.Result;

@CapacitorPlugin(name = "Dialog")
public class DialogPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "DialogPlugin";

    private Dialog implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new Dialog(this);
    }

    @PluginMethod
    public void alert(PluginCall call) {
        try {
            AlertOptions options = new AlertOptions(call);
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
            implementation.alert(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void confirm(PluginCall call) {
        try {
            ConfirmOptions options = new ConfirmOptions(call);
            NonEmptyResultCallback<ConfirmResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull ConfirmResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.confirm(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void prompt(PluginCall call) {
        try {
            PromptOptions options = new PromptOptions(call);
            NonEmptyResultCallback<PromptResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull PromptResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.prompt(options, callback);
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
