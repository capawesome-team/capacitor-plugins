package io.capawesome.capacitorjs.plugins.deviceinfo.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.deviceinfo.interfaces.Result;

public class GetIdResult implements Result {

    @NonNull
    private final String identifier;

    public GetIdResult(@NonNull String identifier) {
        this.identifier = identifier;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("identifier", identifier);
        return result;
    }
}
