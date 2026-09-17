package io.capawesome.capacitorjs.plugins.intercom.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.intercom.classes.CustomExceptions;

public class SetUserJwtOptions {

    @NonNull
    private final String jwt;

    public SetUserJwtOptions(@NonNull PluginCall call) throws Exception {
        String jwt = call.getString("jwt");
        if (jwt == null) {
            throw CustomExceptions.JWT_MISSING;
        }
        this.jwt = jwt;
    }

    @NonNull
    public String getJwt() {
        return jwt;
    }
}
