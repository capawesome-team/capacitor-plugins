package io.capawesome.capacitorjs.plugins.intune.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.intune.IntuneHelper;
import io.capawesome.capacitorjs.plugins.intune.classes.CustomExceptions;

public class ProtectFileOptions {

    @NonNull
    private final String accountId;

    @NonNull
    private final String path;

    public ProtectFileOptions(@NonNull PluginCall call) throws Exception {
        this.accountId = ProtectFileOptions.getAccountIdFromCall(call);
        this.path = ProtectFileOptions.getPathFromCall(call);
    }

    @NonNull
    public String getAccountId() {
        return accountId;
    }

    @NonNull
    public String getPath() {
        return path;
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
    private static String getPathFromCall(@NonNull PluginCall call) throws Exception {
        String path = call.getString("path");
        if (path == null) {
            throw CustomExceptions.PATH_MISSING;
        }
        return IntuneHelper.getFilePath(path);
    }
}
