package io.capawesome.capacitorjs.plugins.realtimekit;

import androidx.annotation.NonNull;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.realtimekit.classes.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.realtimekit.classes.options.StartMeetingOptions;

@CapacitorPlugin(name = "RealtimeKit")
public class RealtimeKitPlugin extends Plugin {

    public static final String ERROR_AUTH_TOKEN_MISSING = "authToken must be provided.";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";

    private static final String TAG = "RealtimeKit";

    private final RealtimeKit implementation = new RealtimeKit(this);

    @PluginMethod
    public void initialize(PluginCall call) {
        resolveCall(call);
    }

    @PluginMethod
    public void startMeeting(PluginCall call) {
        try {
            StartMeetingOptions options = new StartMeetingOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }

                @Override
                public void success() {
                    resolveCall(call);
                }
            };

            implementation.startMeeting(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        Logger.error(TAG, message, exception);
        call.reject(message);
    }

    private void resolveCall(@NonNull PluginCall call) {
        call.resolve();
    }
}
