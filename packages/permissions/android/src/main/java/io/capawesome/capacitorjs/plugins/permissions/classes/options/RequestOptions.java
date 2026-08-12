package io.capawesome.capacitorjs.plugins.permissions.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.permissions.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.permissions.enums.PermissionType;
import java.util.ArrayList;
import java.util.List;

public class RequestOptions {

    @NonNull
    private final List<PermissionType> permissions;

    public RequestOptions(@NonNull PluginCall call) throws Exception {
        this.permissions = RequestOptions.getPermissionsFromCall(call);
    }

    @NonNull
    public List<PermissionType> getPermissions() {
        return permissions;
    }

    @NonNull
    private static List<PermissionType> getPermissionsFromCall(@NonNull PluginCall call) throws Exception {
        JSArray permissions = call.getArray("permissions");
        if (permissions == null || permissions.length() == 0) {
            throw CustomExceptions.PERMISSIONS_MISSING;
        }
        List<PermissionType> result = new ArrayList<>();
        for (int index = 0; index < permissions.length(); index++) {
            result.add(PermissionType.from(permissions.getString(index)));
        }
        return result;
    }
}
