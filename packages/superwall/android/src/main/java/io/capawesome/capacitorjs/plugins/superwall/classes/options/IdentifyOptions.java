package io.capawesome.capacitorjs.plugins.superwall.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.superwall.classes.CustomExceptions;

public class IdentifyOptions {

    @NonNull
    private final String userId;

    @Nullable
    private final JSObject options;

    public IdentifyOptions(@NonNull PluginCall call) throws Exception {
        this.userId = getUserIdFromCall(call);
        this.options = getOptionsFromCall(call);
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    @Nullable
    public JSObject getOptions() {
        return options;
    }

    public boolean getRestorePaywallAssignments() {
        if (options == null) {
            return false;
        }
        Boolean restore = options.getBool("restorePaywallAssignments");
        return restore != null ? restore : false;
    }

    @NonNull
    private static String getUserIdFromCall(@NonNull PluginCall call) throws Exception {
        String userId = call.getString("userId");
        if (userId == null) {
            throw CustomExceptions.USER_ID_MISSING;
        }
        return userId;
    }

    @Nullable
    private static JSObject getOptionsFromCall(@NonNull PluginCall call) {
        return call.getObject("options");
    }
}
