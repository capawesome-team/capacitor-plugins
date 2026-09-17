package io.capawesome.capacitorjs.plugins.intune.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.intune.classes.CustomExceptions;

public class GetAppConfigOptions {

    @NonNull
    private final String accountId;

    public GetAppConfigOptions(@NonNull PluginCall call) throws Exception {
        this.accountId = GetAppConfigOptions.getAccountIdFromCall(call);
    }

    @NonNull
    public String getAccountId() {
        return accountId;
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
