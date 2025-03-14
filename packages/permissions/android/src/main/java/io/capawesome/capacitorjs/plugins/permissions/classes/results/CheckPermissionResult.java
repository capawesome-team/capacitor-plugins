package io.capawesome.capacitorjs.plugins.permissions.classes.results;

import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import com.getcapacitor.JSObject;

import io.capawesome.capacitorjs.plugins.permissions.enums.PermissionState;
import io.capawesome.capacitorjs.plugins.permissions.interfaces.Result;

public class CheckPermissionResult implements Result {

    private final PermissionState state;

    public CheckPermissionResult(int state) throws Exception {
        if (state == PackageManager.PERMISSION_DENIED) {
            this.state = PermissionState.DENIED;
        }
        throw new Exception("Not implement");
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("state", this.state.toString());
        return result;
    }
}
