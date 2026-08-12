package io.capawesome.capacitorjs.plugins.intercom.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.intercom.classes.CustomExceptions;

public class SetUserHashOptions {

    @NonNull
    private final String userHash;

    public SetUserHashOptions(@NonNull PluginCall call) throws Exception {
        String userHash = call.getString("userHash");
        if (userHash == null) {
            throw CustomExceptions.USER_HASH_MISSING;
        }
        this.userHash = userHash;
    }

    @NonNull
    public String getUserHash() {
        return userHash;
    }
}
