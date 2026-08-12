package io.capawesome.capacitorjs.plugins.phonedialer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.phonedialer.classes.CustomException;
import io.capawesome.capacitorjs.plugins.phonedialer.classes.options.DialOptions;
import io.capawesome.capacitorjs.plugins.phonedialer.classes.results.CanDialResult;
import io.capawesome.capacitorjs.plugins.phonedialer.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.phonedialer.interfaces.Result;

@CapacitorPlugin(name = "PhoneDialer")
public class PhoneDialerPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "PhoneDialerPlugin";

    private PhoneDialer implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new PhoneDialer(this);
    }

    @PluginMethod
    public void canDial(PluginCall call) {
        try {
            CanDialResult result = new CanDialResult(implementation.canDial());
            resolveCall(call, result);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void dial(PluginCall call) {
        try {
            if (!implementation.canDial()) {
                rejectCallAsUnavailable(call);
                return;
            }

            DialOptions options = new DialOptions(call);
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

            implementation.dial(options, callback);
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
