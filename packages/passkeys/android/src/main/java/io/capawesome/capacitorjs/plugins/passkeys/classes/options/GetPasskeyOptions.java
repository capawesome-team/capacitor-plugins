package io.capawesome.capacitorjs.plugins.passkeys.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.passkeys.classes.CustomExceptions;

public class GetPasskeyOptions {

    @NonNull
    private final String requestJson;

    public GetPasskeyOptions(@NonNull PluginCall call) throws Exception {
        this.requestJson = GetPasskeyOptions.getRequestJsonFromCall(call);
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
        if (data.getString("rpId") == null) {
            throw CustomExceptions.RP_ID_TOP_LEVEL_MISSING;
        }
        return data.toString();
    }
}
