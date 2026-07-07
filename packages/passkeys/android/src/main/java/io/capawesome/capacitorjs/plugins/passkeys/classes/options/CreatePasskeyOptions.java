package io.capawesome.capacitorjs.plugins.passkeys.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.passkeys.classes.CustomExceptions;

public class CreatePasskeyOptions {

    @NonNull
    private final String requestJson;

    public CreatePasskeyOptions(@NonNull PluginCall call) throws Exception {
        this.requestJson = CreatePasskeyOptions.getRequestJsonFromCall(call);
    }

    @NonNull
    public String getRequestJson() {
        return requestJson;
    }

    @NonNull
    private static String getRequestJsonFromCall(@NonNull PluginCall call) throws Exception {
        JSObject data = call.getData();
        if (data.getString("challenge") == null) {
            throw CustomExceptions.CHALLENGE_MISSING;
        }
        JSObject rp = data.getJSObject("rp");
        if (rp == null || rp.getString("id") == null) {
            throw CustomExceptions.RP_ID_MISSING;
        }
        JSObject user = data.getJSObject("user");
        if (user == null || user.getString("id") == null) {
            throw CustomExceptions.USER_ID_MISSING;
        }
        return data.toString();
    }
}
