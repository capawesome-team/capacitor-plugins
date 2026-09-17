package io.capawesome.capacitorjs.plugins.intercom.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.intercom.interfaces.Result;

public class UnreadConversationCountChangeEvent implements Result {

    private final int count;

    public UnreadConversationCountChangeEvent(int count) {
        this.count = count;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("count", count);
        return result;
    }
}
