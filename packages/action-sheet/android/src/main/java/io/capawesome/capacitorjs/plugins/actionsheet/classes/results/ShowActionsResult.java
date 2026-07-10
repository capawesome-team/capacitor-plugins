package io.capawesome.capacitorjs.plugins.actionsheet.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.actionsheet.interfaces.Result;

public class ShowActionsResult implements Result {

    private final boolean canceled;
    private final int index;

    public ShowActionsResult(int index, boolean canceled) {
        this.index = index;
        this.canceled = canceled;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("canceled", canceled);
        result.put("index", index);
        return result;
    }
}
