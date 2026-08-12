package io.capawesome.capacitorjs.plugins.crisp.classes.events;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.crisp.interfaces.Result;
import org.json.JSONObject;

public class MessageReceivedEvent implements Result {

    @Nullable
    private final String content;

    public MessageReceivedEvent(@Nullable String content) {
        this.content = content;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("content", content == null ? JSONObject.NULL : content);
        return result;
    }
}
