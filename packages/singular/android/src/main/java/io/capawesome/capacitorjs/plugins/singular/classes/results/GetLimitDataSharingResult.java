package io.capawesome.capacitorjs.plugins.singular.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.singular.interfaces.Result;

public class GetLimitDataSharingResult implements Result {

    private final boolean limit;

    public GetLimitDataSharingResult(boolean limit) {
        this.limit = limit;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("limit", limit);
        return result;
    }
}
