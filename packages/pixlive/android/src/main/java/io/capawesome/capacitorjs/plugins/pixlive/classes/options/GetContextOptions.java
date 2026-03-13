package io.capawesome.capacitorjs.plugins.pixlive.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.pixlive.classes.CustomExceptions;

public class GetContextOptions {

    @NonNull
    private final String contextId;

    public GetContextOptions(@NonNull PluginCall call) throws Exception {
        this.contextId = GetContextOptions.getContextIdFromCall(call);
    }

    @NonNull
    public String getContextId() {
        return contextId;
    }

    @NonNull
    private static String getContextIdFromCall(@NonNull PluginCall call) throws Exception {
        String contextId = call.getString("contextId");
        if (contextId == null) {
            throw CustomExceptions.CONTEXT_ID_MISSING;
        }
        return contextId;
    }
}
