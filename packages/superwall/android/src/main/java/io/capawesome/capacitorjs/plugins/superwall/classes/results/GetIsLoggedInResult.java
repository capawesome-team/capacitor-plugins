package io.capawesome.capacitorjs.plugins.superwall.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.superwall.interfaces.Result;

public class GetIsLoggedInResult implements Result {

    private final boolean isLoggedIn;

    public GetIsLoggedInResult(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject jsObject = new JSObject();
        jsObject.put("isLoggedIn", isLoggedIn);
        return jsObject;
    }
}
