package io.capawesome.capacitorjs.plugins.libsql.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;

public class SyncOptions {

    @NonNull
    private final String connectionId;

    public SyncOptions(@NonNull PluginCall call) throws Exception {
        this.connectionId = getConnectionIdFromCall(call);
    }

    @NonNull
    public String getConnectionId() {
        return connectionId;
    }

    @NonNull
    private static String getConnectionIdFromCall(@NonNull PluginCall call) throws Exception {
        String connectionId = call.getString("connectionId");
        if (connectionId == null) {
            throw new Exception("connectionId must be provided.");
        }
        return connectionId;
    }
}