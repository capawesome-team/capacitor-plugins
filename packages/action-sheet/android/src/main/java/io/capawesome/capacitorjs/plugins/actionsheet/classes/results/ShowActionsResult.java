package io.capawesome.capacitorjs.plugins.actionsheet.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.actionsheet.interfaces.Result;

public class ShowActionsResult implements Result {

    private final int index;

    public ShowActionsResult(int index) {
        this.index = index;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("index", index);
        return result;
    }
}
