package io.capawesome.capacitorjs.plugins.intune.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.intune.classes.CustomExceptions;
import java.util.ArrayList;
import java.util.List;

public class AcquireTokenOptions {

    private final boolean forcePrompt;

    @Nullable
    private final String loginHint;

    @NonNull
    private final List<String> scopes;

    public AcquireTokenOptions(@NonNull PluginCall call) throws Exception {
        this.forcePrompt = Boolean.TRUE.equals(call.getBoolean("forcePrompt", false));
        this.loginHint = call.getString("loginHint");
        this.scopes = AcquireTokenOptions.getScopesFromCall(call);
    }

    public boolean getForcePrompt() {
        return forcePrompt;
    }

    @Nullable
    public String getLoginHint() {
        return loginHint;
    }

    @NonNull
    public List<String> getScopes() {
        return scopes;
    }

    @NonNull
    private static List<String> getScopesFromCall(@NonNull PluginCall call) throws Exception {
        JSArray scopes = call.getArray("scopes");
        if (scopes == null) {
            throw CustomExceptions.SCOPES_MISSING;
        }
        List<String> result = new ArrayList<>();
        for (Object scope : scopes.toList()) {
            result.add(scope.toString());
        }
        if (result.isEmpty()) {
            throw CustomExceptions.SCOPES_MISSING;
        }
        return result;
    }
}
