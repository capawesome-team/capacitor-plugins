package io.capawesome.capacitorjs.plugins.libsql.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import java.util.ArrayList;
import java.util.List;

public class ExecuteBatchOptions {

    @NonNull
    private final String connectionId;

    @NonNull
    private final List<String> statement;

    @Nullable
    private final List<List<Object>> values;

    public ExecuteBatchOptions(@NonNull PluginCall call) throws Exception {
        this.connectionId = getConnectionIdFromCall(call);
        this.statement = getStatementFromCall(call);
        this.values = getValuesFromCall(call);
    }

    @NonNull
    public String getConnectionId() {
        return connectionId;
    }

    @NonNull
    public List<String> getStatement() {
        return statement;
    }

    @Nullable
    public List<List<Object>> getValues() {
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
    private static List<String> getStatementFromCall(@NonNull PluginCall call) throws Exception {
        JSArray statementArray = call.getArray("statement");
        if (statementArray == null) {
            throw new Exception("statement must be provided.");
        }

        List<String> statements = new ArrayList<>();
        for (int i = 0; i < statementArray.length(); i++) {
            Object item = statementArray.get(i);
            if (item instanceof String) {
                statements.add((String) item);
            } else {
                throw new Exception("All statements must be strings.");
            }
        }
        return statements;
    }

    @Nullable
    private static List<List<Object>> getValuesFromCall(@NonNull PluginCall call) throws Exception {
        JSArray valuesArray = call.getArray("values");
        if (valuesArray == null) {
            return null;
        }

        List<List<Object>> allValues = new ArrayList<>();
        for (int i = 0; i < valuesArray.length(); i++) {
            Object item = valuesArray.get(i);
            if (item instanceof JSArray) {
                JSArray subArray = (JSArray) item;
                List<Object> subValues = new ArrayList<>();
                for (int j = 0; j < subArray.length(); j++) {
                    subValues.add(subArray.get(j));
                }
                allValues.add(subValues);
            } else {
                throw new Exception("All values must be arrays.");
            }
        }
        return allValues;
    }
}
