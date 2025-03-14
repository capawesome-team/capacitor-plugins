package io.capawesome.capacitorjs.plugins.permissions;

import android.Manifest;
import android.content.Context;

import androidx.core.content.ContextCompat;

import io.capawesome.capacitorjs.plugins.permissions.classes.options.CheckPermissionOptions;
import io.capawesome.capacitorjs.plugins.permissions.classes.results.CheckPermissionResult;
import io.capawesome.capacitorjs.plugins.permissions.enums.Permission;
import io.capawesome.capacitorjs.plugins.permissions.interfaces.Result;

public class Permissions {

    public Result checkPermission(Context context, CheckPermissionOptions options) throws Exception {
        String permission = null;
        if (options.getPermission() == Permission.CAMERA) {
            permission = Manifest.permission.CAMERA;
        }
        if (permission == null) {
            throw new Exception("Not implemented");
        }
        return new CheckPermissionResult(ContextCompat.checkSelfPermission(context, permission));
    }
}
