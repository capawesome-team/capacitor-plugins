package io.capawesome.capacitorjs.plugins.textzoom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.textzoom.classes.options.SetZoomOptions;
import io.capawesome.capacitorjs.plugins.textzoom.classes.results.GetPreferredZoomResult;
import io.capawesome.capacitorjs.plugins.textzoom.classes.results.GetZoomResult;
import io.capawesome.capacitorjs.plugins.textzoom.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.textzoom.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.textzoom.interfaces.Result;

@CapacitorPlugin(name = "TextZoom")
public class TextZoomPlugin extends Plugin {

    public static final String TAG = "TextZoom";

    private static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private TextZoom implementation;

    @Override
    public void load() {
        implementation = new TextZoom(this);
    }

    @PluginMethod
    public void getPreferredZoom(PluginCall call) {
        try {
            NonEmptyResultCallback<GetPreferredZoomResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetPreferredZoomResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getPreferredZoom(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getZoom(PluginCall call) {
        try {
            NonEmptyResultCallback<GetZoomResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetZoomResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getZoom(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setZoom(PluginCall call) {
        try {
            SetZoomOptions options = new SetZoomOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.setZoom(options, callback);
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

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
