package io.capawesome.capacitorjs.plugins.crisp.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.crisp.interfaces.Result;

public class SessionLoadedEvent implements Result {

    @NonNull
    private final String sessionId;

    public SessionLoadedEvent(@NonNull String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("sessionId", sessionId);
        return result;
    }
}
