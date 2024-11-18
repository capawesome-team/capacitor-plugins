package io.capawesome.capacitorjs.plugins.appreview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.appreview.interfaces.EmptyCallback;

@CapacitorPlugin(name = "AppReview")
public class AppReviewPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "AppReviewPlugin";

    @Nullable
    private AppReview implementation;

    @Override
    public void load() {
        implementation = new AppReview(this);
    }

    @PluginMethod
    public void openAppStore(PluginCall call) {
        assert implementation != null;
        try {
            implementation.openAppStore();
            resolveCall(call);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void requestReview(PluginCall call) {
        assert implementation != null;
        try {
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
            implementation.requestReviewFlow(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private void resolveCall(@NonNull PluginCall call) {
        call.resolve();
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        Logger.error(TAG, message, exception);
        call.reject(message);
    }
}
