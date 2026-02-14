package io.capawesome.capacitorjs.plugins.pixlive.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.pixlive.interfaces.Result;

public class GetContextResult implements Result {

    @NonNull
    private final JSObject context;

    public GetContextResult(@NonNull JSObject context) {
        this.context = context;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("context", context);
        return result;
    }
}
