package io.capawesome.capacitorjs.plugins.optionpicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.optionpicker.classes.CustomException;
import io.capawesome.capacitorjs.plugins.optionpicker.classes.options.PresentOptions;
import io.capawesome.capacitorjs.plugins.optionpicker.classes.results.PresentResult;
import io.capawesome.capacitorjs.plugins.optionpicker.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.optionpicker.interfaces.Result;

@CapacitorPlugin(name = "OptionPicker")
public class OptionPickerPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "OptionPickerPlugin";

    private OptionPicker implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new OptionPicker(this);
    }

    @PluginMethod
    public void present(PluginCall call) {
        try {
            PresentOptions options = new PresentOptions(call);
            NonEmptyResultCallback<PresentResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull PresentResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.present(options, callback);
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

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
