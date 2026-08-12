package io.capawesome.capacitorjs.plugins.smscomposer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.smscomposer.classes.CustomException;
import io.capawesome.capacitorjs.plugins.smscomposer.classes.options.ComposeSmsOptions;
import io.capawesome.capacitorjs.plugins.smscomposer.classes.results.CanComposeSmsResult;
import io.capawesome.capacitorjs.plugins.smscomposer.classes.results.ComposeSmsResult;
import io.capawesome.capacitorjs.plugins.smscomposer.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.smscomposer.interfaces.Result;

@CapacitorPlugin(name = "SmsComposer")
public class SmsComposerPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "SmsComposerPlugin";

    private SmsComposer implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new SmsComposer(this);
    }

    @PluginMethod
    public void canComposeSms(PluginCall call) {
        try {
            CanComposeSmsResult result = new CanComposeSmsResult(implementation.canComposeSms());
            resolveCall(call, result);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void composeSms(PluginCall call) {
        try {
            if (!implementation.canComposeSms()) {
                rejectCallAsUnavailable(call);
                return;
            }

            ComposeSmsOptions options = new ComposeSmsOptions(call);
            NonEmptyResultCallback<ComposeSmsResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull ComposeSmsResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.composeSms(options, callback);
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

    private void rejectCallAsUnavailable(@NonNull PluginCall call) {
        call.unavailable("This method is not available on this platform.");
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
