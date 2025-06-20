package io.capawesome.capacitorjs.plugins.libsql.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;

public class RollbackTransactionOptions {

    @NonNull
    private final String connectionId;
    @NonNull
    private final String transactionId;

    public RollbackTransactionOptions(@NonNull PluginCall call) throws Exception {
        this.connectionId = getConnectionIdFromCall(call);
        this.transactionId = getTransactionIdFromCall(call);
    }

    @NonNull
    public String getConnectionId() {
        return connectionId;
    }

    @NonNull
    public String getTransactionId() {
        return transactionId;
    }

    @NonNull
    private static String getConnectionIdFromCall(@NonNull PluginCall call) throws Exception {
        String connectionId = call.getString("connectionId");
        if (connectionId == null) {
            throw new Exception("connectionId must be provided.");
        }
        return connectionId;
    }

    @NonNull
    private static String getTransactionIdFromCall(@NonNull PluginCall call) throws Exception {
        String transactionId = call.getString("transactionId");
        if (transactionId == null) {
            throw new Exception("transactionId must be provided.");
        }
        return transactionId;
    }
}