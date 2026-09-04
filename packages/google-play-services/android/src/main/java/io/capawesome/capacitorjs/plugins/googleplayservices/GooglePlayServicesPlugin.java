package io.capawesome.capacitorjs.plugins.googleplayservices;

import androidx.annotation.NonNull;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.googleplayservices.classes.CustomException;
import io.capawesome.capacitorjs.plugins.googleplayservices.classes.results.GetStatusResult;
import io.capawesome.capacitorjs.plugins.googleplayservices.classes.results.GetVersionResult;
import io.capawesome.capacitorjs.plugins.googleplayservices.classes.results.IsAvailableResult;
import io.capawesome.capacitorjs.plugins.googleplayservices.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.googleplayservices.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.googleplayservices.interfaces.Result;

@CapacitorPlugin(name = "GooglePlayServices")
public class GooglePlayServicesPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "GooglePlayServicesPlugin";

    private GooglePlayServices implementation;

    @PluginMethod
    public void getStatus(PluginCall call) {
        try {
            NonEmptyResultCallback<GetStatusResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetStatusResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getStatus(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getVersion(PluginCall call) {
        try {
            NonEmptyResultCallback<GetVersionResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetVersionResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getVersion(callback);
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

    @Override
    public void load() {
        super.load();
        this.implementation = new GooglePlayServices(this);
    }

    @PluginMethod
    public void makeAvailable(PluginCall call) {
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
            implementation.makeAvailable(callback);
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

    private void resolveCall(@NonNull PluginCall call, @NonNull Result result) {
        call.resolve(result.toJSObject());
    }
}
