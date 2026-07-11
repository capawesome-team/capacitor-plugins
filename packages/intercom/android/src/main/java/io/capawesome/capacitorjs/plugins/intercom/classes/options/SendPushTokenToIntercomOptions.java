package io.capawesome.capacitorjs.plugins.intercom.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.intercom.classes.CustomExceptions;

public class SendPushTokenToIntercomOptions {

    @NonNull
    private final String token;

    public SendPushTokenToIntercomOptions(@NonNull PluginCall call) throws Exception {
        String token = call.getString("token");
        if (token == null) {
            throw CustomExceptions.TOKEN_MISSING;
        }
        this.token = token;
    }

    @NonNull
    public String getToken() {
        return token;
    }
}
