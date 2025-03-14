package io.capawesome.capacitorjs.plugins.permissions.classes.options;

import androidx.annotation.NonNull;

import com.getcapacitor.PluginCall;

import io.capawesome.capacitorjs.plugins.permissions.enums.Permission;

public class CheckPermissionOptions {
    @NonNull
    private final Permission permission;

    public CheckPermissionOptions(@NonNull PluginCall call) throws Exception {
        this.permission = getPermissionFromCall(call);
    }

    @NonNull
    public Permission getPermission() {
        return permission;
    }

    private Permission getPermissionFromCall(PluginCall call) throws Exception {
        String permission = call.getString("permission");
        if (permission == null) {
            throw new Exception();
        }
        return Permission.valueOf(permission);
    }
}
