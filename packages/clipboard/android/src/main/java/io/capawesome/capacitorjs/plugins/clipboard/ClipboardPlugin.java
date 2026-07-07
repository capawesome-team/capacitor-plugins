package io.capawesome.capacitorjs.plugins.clipboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.clipboard.classes.CustomException;
import io.capawesome.capacitorjs.plugins.clipboard.classes.options.WriteOptions;
import io.capawesome.capacitorjs.plugins.clipboard.classes.results.ReadResult;
import io.capawesome.capacitorjs.plugins.clipboard.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.clipboard.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.clipboard.interfaces.Result;

@CapacitorPlugin(name = "Clipboard")
public class ClipboardPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "ClipboardPlugin";

    private Clipboard implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new Clipboard(this);
    }

    @PluginMethod
    public void read(PluginCall call) {
        try {
            NonEmptyResultCallback<ReadResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull ReadResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.read(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void write(PluginCall call) {
        try {
            WriteOptions options = new WriteOptions(call);
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
            implementation.write(options, callback);
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
