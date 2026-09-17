package io.capawesome.capacitorjs.plugins.googleplayservices.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.googleplayservices.interfaces.Result;

public class GetVersionResult implements Result {

    private final int version;

    public GetVersionResult(int version) {
        this.version = version;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("version", version);
        return result;
    }
}
