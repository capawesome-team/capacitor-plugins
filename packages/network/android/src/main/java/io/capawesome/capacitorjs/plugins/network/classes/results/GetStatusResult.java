package io.capawesome.capacitorjs.plugins.network.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.network.interfaces.Result;

public class GetStatusResult implements Result {

    private final boolean connected;

    @NonNull
    private final String connectionType;

    private final boolean internetReachable;

    public GetStatusResult(boolean connected, @NonNull String connectionType, boolean internetReachable) {
        this.connected = connected;
        this.connectionType = connectionType;
        this.internetReachable = internetReachable;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("connected", connected);
        result.put("connectionType", connectionType);
        result.put("internetReachable", internetReachable);
        return result;
    }
}
