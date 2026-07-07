package io.capawesome.capacitorjs.plugins.passkeys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.passkeys.classes.CustomException;
import io.capawesome.capacitorjs.plugins.passkeys.classes.options.CreatePasskeyOptions;
import io.capawesome.capacitorjs.plugins.passkeys.classes.options.GetPasskeyOptions;
import io.capawesome.capacitorjs.plugins.passkeys.classes.results.CreatePasskeyResult;
import io.capawesome.capacitorjs.plugins.passkeys.classes.results.GetPasskeyResult;
import io.capawesome.capacitorjs.plugins.passkeys.classes.results.IsAvailableResult;
import io.capawesome.capacitorjs.plugins.passkeys.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.passkeys.interfaces.Result;

@CapacitorPlugin(name = "Passkeys")
public class PasskeysPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "PasskeysPlugin";

    private Passkeys implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new Passkeys(this);
    }

    @PluginMethod
    public void createPasskey(PluginCall call) {
        try {
            CreatePasskeyOptions options = new CreatePasskeyOptions(call);
            NonEmptyResultCallback<CreatePasskeyResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull CreatePasskeyResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.createPasskey(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getPasskey(PluginCall call) {
        try {
            GetPasskeyOptions options = new GetPasskeyOptions(call);
            NonEmptyResultCallback<GetPasskeyResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetPasskeyResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getPasskey(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isAvailable(PluginCall call) {
        try {
            NonEmptyResultCallback<IsAvailableResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsAvailableResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.isAvailable(callback);
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
