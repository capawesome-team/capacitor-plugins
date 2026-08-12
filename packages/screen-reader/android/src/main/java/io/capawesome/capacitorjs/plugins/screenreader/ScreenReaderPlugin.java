package io.capawesome.capacitorjs.plugins.screenreader;

import androidx.annotation.NonNull;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.screenreader.classes.CustomException;
import io.capawesome.capacitorjs.plugins.screenreader.classes.events.StateChangeEvent;
import io.capawesome.capacitorjs.plugins.screenreader.classes.options.AnnounceOptions;
import io.capawesome.capacitorjs.plugins.screenreader.classes.results.IsEnabledResult;
import io.capawesome.capacitorjs.plugins.screenreader.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.screenreader.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.screenreader.interfaces.Result;

@CapacitorPlugin(name = "ScreenReader")
public class ScreenReaderPlugin extends Plugin {

    public static final String EVENT_STATE_CHANGE = "stateChange";

    public static final String TAG = "ScreenReaderPlugin";

    private static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";

    private ScreenReader implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new ScreenReader(this);
    }

    @Override
    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void addListener(PluginCall call) {
        super.addListener(call);
        implementation.startObserving();
    }

    @PluginMethod
    public void announce(PluginCall call) {
        try {
            AnnounceOptions options = new AnnounceOptions(call);
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
            implementation.announce(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isEnabled(PluginCall call) {
        try {
            NonEmptyResultCallback<IsEnabledResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsEnabledResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.isEnabled(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void notifyStateChangeListeners(@NonNull StateChangeEvent event) {
        notifyListeners(EVENT_STATE_CHANGE, event.toJSObject());
    }

    @Override
    @PluginMethod
    public void removeAllListeners(PluginCall call) {
        super.removeAllListeners(call);
        implementation.stopObserving();
    }

    @Override
    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void removeListener(PluginCall call) {
        super.removeListener(call);
        if (!hasListeners(EVENT_STATE_CHANGE)) {
            implementation.stopObserving();
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

    private void resolveCall(@NonNull PluginCall call, @NonNull Result result) {
        call.resolve(result.toJSObject());
    }
}
