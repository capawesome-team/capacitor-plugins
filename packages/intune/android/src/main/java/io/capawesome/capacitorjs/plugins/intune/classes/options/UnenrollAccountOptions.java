package io.capawesome.capacitorjs.plugins.intune.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.intune.classes.CustomExceptions;

public class UnenrollAccountOptions {

    @NonNull
    private final String accountId;

    private final boolean wipe;

    public UnenrollAccountOptions(@NonNull PluginCall call) throws Exception {
        this.accountId = UnenrollAccountOptions.getAccountIdFromCall(call);
        this.wipe = Boolean.TRUE.equals(call.getBoolean("wipe", false));
    }

    @NonNull
    public String getAccountId() {
        return accountId;
    }

    public boolean getWipe() {
        return wipe;
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
