package io.capawesome.capacitorjs.plugins.silentmode.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.silentmode.interfaces.Result;

public class IsSilentResult implements Result {

    private final boolean silent;

    public IsSilentResult(boolean silent) {
        this.silent = silent;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("silent", silent);
        return result;
    }
}
