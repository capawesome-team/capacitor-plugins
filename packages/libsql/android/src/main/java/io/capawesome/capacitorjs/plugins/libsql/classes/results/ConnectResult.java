package io.capawesome.capacitorjs.plugins.libsql.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.libsql.interfaces.Result;

public class ConnectResult implements Result {

    @NonNull
    private final String connectionId;

    public ConnectResult(@NonNull String connectionId) {
        this.connectionId = connectionId;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("connectionId", connectionId);
        return result;
    }
}
