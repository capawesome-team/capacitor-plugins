package io.capawesome.capacitorjs.plugins.navigationbar.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.navigationbar.interfaces.Result;

public class GetStyleResult implements Result {

    @NonNull
    private final String style;

    public GetStyleResult(@NonNull String style) {
        this.style = style;
    }

    @Override
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("style", style);
        return result;
    }
}
