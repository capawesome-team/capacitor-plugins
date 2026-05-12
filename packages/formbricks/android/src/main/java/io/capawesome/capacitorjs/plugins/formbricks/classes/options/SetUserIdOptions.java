package io.capawesome.capacitorjs.plugins.formbricks.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.formbricks.FormbricksPlugin;

public class SetUserIdOptions {

    @NonNull
    private final String userId;

    public SetUserIdOptions(@NonNull PluginCall call) throws Exception {
        String userId = call.getString("userId");
        if (userId == null) {
            throw new Exception(FormbricksPlugin.ERROR_USER_ID_MISSING);
        }
        this.userId = userId;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }
}
