package io.capawesome.capacitorjs.plugins.rootdetection.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.rootdetection.interfaces.Result;

public class IsRootedResult implements Result {

    private final boolean rooted;

    public IsRootedResult(boolean rooted) {
        this.rooted = rooted;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("rooted", rooted);
        return result;
    }
}
