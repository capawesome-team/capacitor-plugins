package io.capawesome.capacitorjs.plugins.superwall.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.superwall.interfaces.Result;

public class GetUserIdResult implements Result {

    @NonNull
    private final String userId;

    public GetUserIdResult(@NonNull String userId) {
        this.userId = userId;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject jsObject = new JSObject();
        jsObject.put("userId", userId);
        return jsObject;
    }
}
