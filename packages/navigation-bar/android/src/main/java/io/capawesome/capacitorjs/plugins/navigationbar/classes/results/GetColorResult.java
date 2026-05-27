package io.capawesome.capacitorjs.plugins.navigationbar.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.navigationbar.interfaces.Result;

public class GetColorResult implements Result {

    @NonNull
    private final String color;

    public GetColorResult(@NonNull String color) {
        this.color = color;
    }

    @Override
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("color", color);
        return result;
    }
}
