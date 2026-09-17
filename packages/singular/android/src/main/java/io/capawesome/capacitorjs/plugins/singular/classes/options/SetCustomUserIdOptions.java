package io.capawesome.capacitorjs.plugins.singular.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.singular.classes.CustomExceptions;

public class SetCustomUserIdOptions {

    @NonNull
    private final String customUserId;

    public SetCustomUserIdOptions(@NonNull PluginCall call) throws Exception {
        String customUserId = call.getString("customUserId");
        if (customUserId == null) {
            throw CustomExceptions.CUSTOM_USER_ID_MISSING;
        }
        this.customUserId = customUserId;
    }

    @NonNull
    public String getCustomUserId() {
        return customUserId;
    }
}
