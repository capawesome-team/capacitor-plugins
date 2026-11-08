package io.capawesome.capacitorjs.plugins.intune.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.intune.classes.CustomExceptions;
import java.util.ArrayList;
import java.util.List;

public class AcquireTokenSilentOptions {

    @NonNull
    private final String accountId;

    @Nullable
    private final String authority;

    @Nullable
    private final String clientId;

    private final boolean forceRefresh;

    @NonNull
    private final List<String> scopes;

    public AcquireTokenSilentOptions(@NonNull PluginCall call) throws Exception {
        this.accountId = AcquireTokenSilentOptions.getAccountIdFromCall(call);
        this.authority = call.getString("authority");
        this.clientId = call.getString("clientId");
        this.forceRefresh = Boolean.TRUE.equals(call.getBoolean("forceRefresh", false));
        this.scopes = AcquireTokenSilentOptions.getScopesFromCall(call);
    }

    @NonNull
    public String getAccountId() {
        return accountId;
    }

    @Nullable
    public String getAuthority() {
        return authority;
    }

    @Nullable
    public String getClientId() {
        return clientId;
    }

    public boolean getForceRefresh() {
        return forceRefresh;
    }

    @NonNull
    public List<String> getScopes() {
        return scopes;
    }

    @NonNull
    private static String getAccountIdFromCall(@NonNull PluginCall call) throws Exception {
        String accountId = call.getString("accountId");
        if (accountId == null) {
            throw CustomExceptions.ACCOUNT_ID_MISSING;
        }
        return accountId;
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
