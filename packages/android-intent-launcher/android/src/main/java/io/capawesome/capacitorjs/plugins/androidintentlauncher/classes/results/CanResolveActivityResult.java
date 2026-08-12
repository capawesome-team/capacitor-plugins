package io.capawesome.capacitorjs.plugins.androidintentlauncher.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.androidintentlauncher.interfaces.Result;

public class CanResolveActivityResult implements Result {

    private final boolean canResolve;

    public CanResolveActivityResult(boolean canResolve) {
        this.canResolve = canResolve;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("canResolve", canResolve);
        return result;
    }
}
