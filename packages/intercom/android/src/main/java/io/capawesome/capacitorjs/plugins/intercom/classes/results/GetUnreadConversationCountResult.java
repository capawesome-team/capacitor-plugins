package io.capawesome.capacitorjs.plugins.intercom.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.intercom.interfaces.Result;

public class GetUnreadConversationCountResult implements Result {

    private final int count;

    public GetUnreadConversationCountResult(int count) {
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
