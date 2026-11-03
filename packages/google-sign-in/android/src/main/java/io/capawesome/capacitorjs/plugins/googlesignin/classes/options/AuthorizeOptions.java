package io.capawesome.capacitorjs.plugins.googlesignin.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.googlesignin.classes.CustomExceptions;
import java.util.List;

public class AuthorizeOptions {

    @NonNull
    private final String[] scopes;

    public AuthorizeOptions(@NonNull PluginCall call) throws Exception {
        this.scopes = getScopesFromCall(call);
    }

    @NonNull
    public String[] getScopes() {
        return scopes;
    }

    @NonNull
    private static String[] getScopesFromCall(@NonNull PluginCall call) throws Exception {
        JSArray scopesArray = call.getArray("scopes");
        if (scopesArray == null) {
            throw CustomExceptions.SCOPES_MISSING;
        }
        List<String> scopesList = scopesArray.toList();
        if (scopesList.isEmpty()) {
            throw CustomExceptions.SCOPES_MISSING;
        }
        return scopesList.toArray(new String[0]);
    }
}
