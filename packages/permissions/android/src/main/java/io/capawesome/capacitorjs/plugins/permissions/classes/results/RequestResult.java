package io.capawesome.capacitorjs.plugins.permissions.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.permissions.interfaces.Result;
import java.util.List;

public class RequestResult implements Result {

    @NonNull
    private final List<PermissionStatus> statuses;

    public RequestResult(@NonNull List<PermissionStatus> statuses) {
        this.statuses = statuses;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSArray statusesResult = new JSArray();
        for (PermissionStatus status : statuses) {
            statusesResult.put(status.toJSObject());
        }
        JSObject result = new JSObject();
        result.put("statuses", statusesResult);
        return result;
    }
}
