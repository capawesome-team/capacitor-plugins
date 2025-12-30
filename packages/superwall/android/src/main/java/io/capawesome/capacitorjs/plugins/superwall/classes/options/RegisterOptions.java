package io.capawesome.capacitorjs.plugins.superwall.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.superwall.SuperwallHelper;
import io.capawesome.capacitorjs.plugins.superwall.classes.CustomExceptions;
import java.util.Map;

public class RegisterOptions {

    @NonNull
    private final String placement;

    @Nullable
    private final Map<String, Object> params;

    public RegisterOptions(@NonNull PluginCall call) throws Exception {
        this.placement = getPlacementFromCall(call);
        this.params = SuperwallHelper.createHashMapFromJSONObject(getParamsFromCall(call));
    }

    @NonNull
    public String getPlacement() {
        return placement;
    }

    @Nullable
    public Map<String, Object> getParams() {
        return params;
    }

    @NonNull
    private static String getPlacementFromCall(@NonNull PluginCall call) throws Exception {
        String placement = call.getString("placement");
        if (placement == null) {
            throw CustomExceptions.PLACEMENT_MISSING;
        }
        return placement;
    }

    @Nullable
    private static JSObject getParamsFromCall(@NonNull PluginCall call) {
        return call.getObject("params");
    }
}
