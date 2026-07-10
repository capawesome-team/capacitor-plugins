package io.capawesome.capacitorjs.plugins.wallet.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.wallet.classes.CustomExceptions;

public class SaveToGoogleWalletOptions {

    @NonNull
    private final String jwt;

    public SaveToGoogleWalletOptions(@NonNull PluginCall call) throws Exception {
        this.jwt = getJwtFromCall(call);
    }

    @NonNull
    public String getJwt() {
        return jwt;
    }

    @NonNull
    private static String getJwtFromCall(@NonNull PluginCall call) throws Exception {
        String jwt = call.getString("jwt");
        if (jwt == null || jwt.isEmpty()) {
            throw CustomExceptions.JWT_MISSING;
        }
        return jwt;
    }
}
