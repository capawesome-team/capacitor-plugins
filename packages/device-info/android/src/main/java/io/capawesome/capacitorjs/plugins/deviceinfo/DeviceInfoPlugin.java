package io.capawesome.capacitorjs.plugins.deviceinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.deviceinfo.classes.results.GetIdResult;
import io.capawesome.capacitorjs.plugins.deviceinfo.classes.results.GetInfoResult;
import io.capawesome.capacitorjs.plugins.deviceinfo.classes.results.GetUptimeResult;
import io.capawesome.capacitorjs.plugins.deviceinfo.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.deviceinfo.interfaces.Result;

@CapacitorPlugin(name = "DeviceInfo")
public class DeviceInfoPlugin extends Plugin {

    public static final String TAG = "DeviceInfo";

    private static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private DeviceInfo implementation;

    @Override
    public void load() {
        implementation = new DeviceInfo(getContext());
    }

    @PluginMethod
    public void getId(PluginCall call) {
        try {
            NonEmptyResultCallback<GetIdResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetIdResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getId(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getInfo(PluginCall call) {
        try {
            NonEmptyResultCallback<GetInfoResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetInfoResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getInfo(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getUptime(PluginCall call) {
        try {
            NonEmptyResultCallback<GetUptimeResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetUptimeResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getUptime(callback);
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

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
