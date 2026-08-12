package io.capawesome.capacitorjs.plugins.passwordautofill;

import androidx.annotation.NonNull;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.passwordautofill.classes.CustomException;
import io.capawesome.capacitorjs.plugins.passwordautofill.classes.options.SavePasswordOptions;
import io.capawesome.capacitorjs.plugins.passwordautofill.interfaces.EmptyCallback;

@CapacitorPlugin(name = "PasswordAutofill")
public class PasswordAutofillPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "PasswordAutofillPlugin";

    private PasswordAutofill implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new PasswordAutofill(this);
    }

    @PluginMethod
    public void savePassword(PluginCall call) {
        try {
            SavePasswordOptions options = new SavePasswordOptions(call);
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
            implementation.savePassword(options, callback);
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
