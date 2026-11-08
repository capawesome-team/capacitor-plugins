package io.capawesome.capacitorjs.plugins.intune.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.intune.classes.CustomExceptions;

public class RegisterAndEnrollAccountOptions {

    @NonNull
    private final String accountId;

    @Nullable
    private final String authority;

    @Nullable
    private final String clientId;

    public RegisterAndEnrollAccountOptions(@NonNull PluginCall call) throws Exception {
        this.accountId = RegisterAndEnrollAccountOptions.getAccountIdFromCall(call);
        this.authority = call.getString("authority");
        this.clientId = call.getString("clientId");
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

    @NonNull
    private static String getAccountIdFromCall(@NonNull PluginCall call) throws Exception {
        String accountId = call.getString("accountId");
        if (accountId == null) {
            throw CustomExceptions.ACCOUNT_ID_MISSING;
        }
        return accountId;
    }
}
