package io.capawesome.capacitorjs.plugins.superwall.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.superwall.interfaces.Result;

public class RegisterResult implements Result {

    @NonNull
    private final String result;

    public RegisterResult(@NonNull String result) {
        this.result = result;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject jsObject = new JSObject();
        jsObject.put("result", result);
        return jsObject;
    }
}
