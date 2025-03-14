package io.capawesome.capacitorjs.plugins.permissions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import io.capawesome.capacitorjs.plugins.permissions.classes.options.CheckPermissionOptions;
import io.capawesome.capacitorjs.plugins.permissions.interfaces.Result;

@CapacitorPlugin(name = "Permissions")
public class PermissionsPlugin extends Plugin {
    public static final String TAG = "Permissions";

    private final Permissions implementation = new Permissions();

    @PluginMethod
    public void checkPermission(PluginCall call) {
        try {
            CheckPermissionOptions checkPermissionOptions = new CheckPermissionOptions(call);
            Result result = implementation.checkPermission(this.bridge.getContext(), checkPermissionOptions);
            resolveCall(call, result.toJSObject());
        } catch (Exception e) {
            rejectCall(call, e);
        }
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable JSObject result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result);
        }
    }

    private void rejectCall(PluginCall call, Exception exception) {
        String message = exception.getMessage();
        Logger.error(TAG, message, exception);
        call.reject(message);
    }
}
