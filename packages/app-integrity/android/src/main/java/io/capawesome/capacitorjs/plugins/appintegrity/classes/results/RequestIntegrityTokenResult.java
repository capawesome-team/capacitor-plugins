package io.capawesome.capacitorjs.plugins.appintegrity.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.appintegrity.interfaces.Result;

public class RequestIntegrityTokenResult implements Result {

    @NonNull
    private final String token;

    public RequestIntegrityTokenResult(@NonNull String token) {
        this.token = token;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("token", token);
        return result;
    }
}
