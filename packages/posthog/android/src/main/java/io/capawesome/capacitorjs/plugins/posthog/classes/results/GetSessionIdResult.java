package io.capawesome.capacitorjs.plugins.posthog.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.posthog.interfaces.Result;
import org.json.JSONObject;

public class GetSessionIdResult implements Result {

    @Nullable
    private final String sessionId;

    public GetSessionIdResult(@Nullable String sessionId) {
        this.sessionId = sessionId;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("sessionId", this.sessionId == null ? JSONObject.NULL : this.sessionId);
        return result;
    }
} 