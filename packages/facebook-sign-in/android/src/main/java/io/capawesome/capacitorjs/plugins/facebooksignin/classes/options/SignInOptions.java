package io.capawesome.capacitorjs.plugins.facebooksignin.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import java.util.Arrays;
import java.util.List;

public class SignInOptions {

    @NonNull
    private final List<String> permissions;

    public SignInOptions(@NonNull PluginCall call) {
        this.permissions = getPermissionsFromCall(call);
    }

    @NonNull
    public List<String> getPermissions() {
        return permissions;
    }

    @NonNull
    private static List<String> getPermissionsFromCall(@NonNull PluginCall call) {
        List<String> defaultPermissions = Arrays.asList("public_profile", "email");
        JSArray permissionsArray = call.getArray("permissions");
        if (permissionsArray == null) {
            return defaultPermissions;
        }
        try {
            return permissionsArray.toList();
        } catch (Exception exception) {
            return defaultPermissions;
        }
    }
}
