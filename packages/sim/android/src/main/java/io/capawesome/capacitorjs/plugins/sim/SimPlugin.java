package io.capawesome.capacitorjs.plugins.sim;

import android.Manifest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import io.capawesome.capacitorjs.plugins.sim.classes.results.GetSimCardsResult;
import io.capawesome.capacitorjs.plugins.sim.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.sim.interfaces.Result;

@CapacitorPlugin(
    name = "Sim",
    permissions = { @Permission(strings = { Manifest.permission.READ_PHONE_STATE }, alias = SimPlugin.PERMISSION_READ_SIM_CARDS) }
)
public class SimPlugin extends Plugin {

    public static final String ERROR_PERMISSION_DENIED = "Permission to read the SIM cards is denied.";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";
    public static final String PERMISSION_READ_SIM_CARDS = "readSimCards";
    public static final String TAG = "Sim";

    private Sim implementation;

    @Override
    public void load() {
        implementation = new Sim(this);
    }

    @PluginMethod
    public void getSimCards(PluginCall call) {
        try {
            if (getPermissionState(PERMISSION_READ_SIM_CARDS) != PermissionState.GRANTED) {
                rejectCall(call, new Exception(ERROR_PERMISSION_DENIED));
                return;
            }

            NonEmptyResultCallback<GetSimCardsResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetSimCardsResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getSimCards(callback);
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
