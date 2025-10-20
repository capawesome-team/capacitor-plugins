package io.capawesome.capacitorjs.plugins.assetmanager.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.assetmanager.interfaces.Result;

public class ReadResult implements Result {

    @NonNull
    private String data;

    public ReadResult(@NonNull String data) {
        this.data = data;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("data", data);
        return result;
    }
}
