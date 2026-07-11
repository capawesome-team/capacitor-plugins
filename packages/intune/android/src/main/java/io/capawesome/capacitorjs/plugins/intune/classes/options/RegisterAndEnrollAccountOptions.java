package io.capawesome.capacitorjs.plugins.intune.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.intune.classes.CustomExceptions;

public class RegisterAndEnrollAccountOptions {

    @NonNull
    private final String accountId;

    public RegisterAndEnrollAccountOptions(@NonNull PluginCall call) throws Exception {
        this.accountId = RegisterAndEnrollAccountOptions.getAccountIdFromCall(call);
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
