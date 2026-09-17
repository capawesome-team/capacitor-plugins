package io.capawesome.capacitorjs.plugins.tiktokappevents;

import androidx.annotation.NonNull;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.tiktokappevents.classes.CustomException;
import io.capawesome.capacitorjs.plugins.tiktokappevents.classes.options.IdentifyOptions;
import io.capawesome.capacitorjs.plugins.tiktokappevents.classes.options.InitializeOptions;
import io.capawesome.capacitorjs.plugins.tiktokappevents.classes.options.TrackEventOptions;
import io.capawesome.capacitorjs.plugins.tiktokappevents.interfaces.EmptyCallback;

@CapacitorPlugin(name = "TiktokAppEvents")
public class TiktokAppEventsPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "TiktokAppEventsPlugin";

    private TiktokAppEvents implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new TiktokAppEvents(this);
    }

    @PluginMethod
    public void flush(PluginCall call) {
        try {
            implementation.flush(createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void identify(PluginCall call) {
        try {
            IdentifyOptions options = new IdentifyOptions(call);
            implementation.identify(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void initialize(PluginCall call) {
        try {
            InitializeOptions options = new InitializeOptions(call);
            implementation.initialize(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void logout(PluginCall call) {
        try {
            implementation.logout(createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void trackEvent(PluginCall call) {
        try {
            TrackEventOptions options = new TrackEventOptions(call);
            implementation.trackEvent(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @NonNull
    private EmptyCallback createEmptyCallback(@NonNull PluginCall call) {
        return new EmptyCallback() {
            @Override
            public void success() {
                resolveCall(call);
            }

            @Override
            public void error(Exception exception) {
                rejectCall(call, exception);
            }
        };
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
