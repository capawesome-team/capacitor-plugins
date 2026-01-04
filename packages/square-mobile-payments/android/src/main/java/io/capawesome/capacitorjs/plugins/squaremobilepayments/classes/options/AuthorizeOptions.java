package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.CustomExceptions;

public class AuthorizeOptions {

    @NonNull
    private final String accessToken;

    public AuthorizeOptions(@NonNull PluginCall call) throws Exception {
        this.accessToken = AuthorizeOptions.getAccessTokenFromCall(call);
    }

    @NonNull
    public String getAccessToken() {
        return accessToken;
    }

    @NonNull
    private static String getAccessTokenFromCall(@NonNull PluginCall call) throws Exception {
        String accessToken = call.getString("accessToken");
        if (accessToken == null) {
            throw CustomExceptions.ACCESS_TOKEN_MISSING;
        }
        return accessToken;
    }
}
