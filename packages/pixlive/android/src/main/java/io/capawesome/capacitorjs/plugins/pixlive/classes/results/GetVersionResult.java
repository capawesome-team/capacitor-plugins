package io.capawesome.capacitorjs.plugins.pixlive.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.pixlive.interfaces.Result;

public class GetVersionResult implements Result {

    @NonNull
    private final String version;

    public GetVersionResult(@NonNull String version) {
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
