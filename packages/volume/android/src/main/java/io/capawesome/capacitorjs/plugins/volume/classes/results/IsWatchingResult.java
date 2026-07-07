package io.capawesome.capacitorjs.plugins.volume.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.volume.interfaces.Result;

public class IsWatchingResult implements Result {

    private final boolean watching;

    public IsWatchingResult(boolean watching) {
        this.watching = watching;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("watching", watching);
        return result;
    }
}
