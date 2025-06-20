package io.capawesome.capacitorjs.plugins.libsql.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import com.getcapacitor.JSArray;
import java.util.List;
import java.util.ArrayList;

public class ExecuteOptions {

    @NonNull
    private final String connectionId;
    @NonNull
    private final String statement;
    @Nullable
    private final String transactionId;
    @Nullable
    private final List<Object> values;

    public ExecuteOptions(@NonNull PluginCall call) throws Exception {
        this.connectionId = getConnectionIdFromCall(call);
        this.statement = getStatementFromCall(call);
        this.transactionId = call.getString("transactionId");
        this.values = getValuesFromCall(call);
    }

    @NonNull
    public String getConnectionId() {
        return connectionId;
    }

    @NonNull
    public String getStatement() {
        return statement;
    }

    @Nullable
    public String getTransactionId() {
        return transactionId;
    }

    @Nullable
    public List<Object> getValues() {
        return values;
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
    private static String getStatementFromCall(@NonNull PluginCall call) throws Exception {
        String statement = call.getString("statement");
        if (statement == null) {
            throw new Exception("statement must be provided.");
        }
        return statement;
    }

    @Nullable
    private static List<Object> getValuesFromCall(@NonNull PluginCall call) throws Exception {
        JSArray valuesArray = call.getArray("values");
        if (valuesArray == null) {
            return null;
        }
        
        List<Object> values = new ArrayList<>();
        for (int i = 0; i < valuesArray.length(); i++) {
            values.add(valuesArray.get(i));
        }
        return values;
    }
}