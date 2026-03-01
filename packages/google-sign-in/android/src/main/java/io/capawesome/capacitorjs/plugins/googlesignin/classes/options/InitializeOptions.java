package io.capawesome.capacitorjs.plugins.googlesignin.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.googlesignin.classes.CustomExceptions;
import java.util.ArrayList;
import java.util.List;

public class InitializeOptions {

    @NonNull
    private final String clientId;

    @Nullable
    private final String[] scopes;

    public InitializeOptions(@NonNull PluginCall call) throws Exception {
        this.clientId = getClientIdFromCall(call);
        this.scopes = getScopesFromCall(call);
    }

    @NonNull
    public String getClientId() {
        return clientId;
    }

    @Nullable
    public String[] getScopes() {
        return scopes;
    }

    @NonNull
    private static String getClientIdFromCall(@NonNull PluginCall call) throws Exception {
        String clientId = call.getString("clientId");
        if (clientId == null) {
            throw CustomExceptions.CLIENT_ID_MISSING;
        }
        return clientId;
    }

    @Nullable
    private static String[] getScopesFromCall(@NonNull PluginCall call) {
        JSArray scopesArray = call.getArray("scopes");
        if (scopesArray == null) {
            return null;
        }
        try {
            List<String> scopesList = scopesArray.toList();
            return scopesList.toArray(new String[0]);
        } catch (Exception e) {
            return null;
        }
    }
}
