package io.capawesome.capacitorjs.plugins.posthog.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.posthog.interfaces.Result;

public class GetDistinctIdResult implements Result {

    @NonNull
    private final String distinctId;

    public GetDistinctIdResult(@NonNull String distinctId) {
        this.distinctId = distinctId;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("distinctId", this.distinctId);
        return result;
    }
}
