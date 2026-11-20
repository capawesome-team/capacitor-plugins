package io.capawesome.capacitorjs.plugins.intune.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.intune.classes.CustomExceptions;

public class RemediateComplianceOptions {

    @NonNull
    private final String accountId;

    @NonNull
    private final String authority;

    @Nullable
    private final String clientId;

    private final boolean silent;

    @NonNull
    private final String tenantId;

    @NonNull
    private final String username;

    public RemediateComplianceOptions(@NonNull PluginCall call) throws Exception {
        this.accountId = RemediateComplianceOptions.getAccountIdFromCall(call);
        this.authority = RemediateComplianceOptions.getAuthorityFromCall(call);
        this.clientId = call.getString("clientId");
        this.silent = Boolean.TRUE.equals(call.getBoolean("silent", false));
        this.tenantId = RemediateComplianceOptions.getTenantIdFromCall(call);
        this.username = RemediateComplianceOptions.getUsernameFromCall(call);
    }

    @NonNull
    public String getAccountId() {
        return accountId;
    }

    @NonNull
    public String getAuthority() {
        return authority;
    }

    @Nullable
    public String getClientId() {
        return clientId;
    }

    public boolean getSilent() {
        return silent;
    }

    @NonNull
    public String getTenantId() {
        return tenantId;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    @NonNull
    private static String getAccountIdFromCall(@NonNull PluginCall call) throws Exception {
        String accountId = call.getString("accountId");
        if (accountId == null || accountId.isEmpty()) {
            throw CustomExceptions.ACCOUNT_ID_MISSING;
        }
        return accountId;
    }

    @NonNull
    private static String getAuthorityFromCall(@NonNull PluginCall call) throws Exception {
        String authority = call.getString("authority");
        if (authority == null || authority.isEmpty()) {
            throw CustomExceptions.AUTHORITY_MISSING;
        }
        return authority;
    }

    @NonNull
    private static String getTenantIdFromCall(@NonNull PluginCall call) throws Exception {
        String tenantId = call.getString("tenantId");
        if (tenantId == null || tenantId.isEmpty()) {
            throw CustomExceptions.TENANT_ID_MISSING;
        }
        return tenantId;
    }

    @NonNull
    private static String getUsernameFromCall(@NonNull PluginCall call) throws Exception {
        String username = call.getString("username");
        if (username == null || username.isEmpty()) {
            throw CustomExceptions.USERNAME_MISSING;
        }
        return username;
    }
}
