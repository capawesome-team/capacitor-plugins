package io.capawesome.capacitorjs.plugins.permissions.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.permissions.enums.PermissionState;
import io.capawesome.capacitorjs.plugins.permissions.enums.PermissionType;
import io.capawesome.capacitorjs.plugins.permissions.interfaces.Result;

public class PermissionStatus implements Result {

    @NonNull
    private final PermissionType permission;

    @NonNull
    private final PermissionState state;

    public PermissionStatus(@NonNull PermissionType permission, @NonNull PermissionState state) {
        this.permission = permission;
        this.state = state;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("permission", permission.getValue());
        result.put("state", state.getValue());
        return result;
    }
}
