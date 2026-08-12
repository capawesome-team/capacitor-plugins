package io.capawesome.capacitorjs.plugins.crisp.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.crisp.classes.CustomExceptions;

public class SetTokenIdOptions {

    @NonNull
    private final String tokenId;

    public SetTokenIdOptions(@NonNull PluginCall call) throws Exception {
        this.tokenId = getTokenIdFromCall(call);
    }

    @NonNull
    public String getTokenId() {
        return tokenId;
    }

    @NonNull
    private static String getTokenIdFromCall(@NonNull PluginCall call) throws Exception {
        String tokenId = call.getString("tokenId");
        if (tokenId == null) {
            throw CustomExceptions.VALUE_MISSING;
        }
        return tokenId;
    }
}
