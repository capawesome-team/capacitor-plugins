package io.capawesome.capacitorjs.plugins.network.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.network.interfaces.Result;

public class GetStatusResult implements Result {

    private final boolean connected;

    @NonNull
    private final String connectionType;

    private final boolean internetReachable;

    private final boolean constrained;

    private final boolean expensive;

    private final boolean ultraConstrained;

    public GetStatusResult(
        boolean connected,
        @NonNull String connectionType,
        boolean internetReachable,
        boolean constrained,
        boolean expensive,
        boolean ultraConstrained
    ) {
        this.connected = connected;
        this.connectionType = connectionType;
        this.internetReachable = internetReachable;
        this.constrained = constrained;
        this.expensive = expensive;
        this.ultraConstrained = ultraConstrained;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("connected", connected);
        result.put("connectionType", connectionType);
        result.put("internetReachable", internetReachable);
        result.put("constrained", constrained);
        result.put("expensive", expensive);
        result.put("ultraConstrained", ultraConstrained);
        return result;
    }
}
