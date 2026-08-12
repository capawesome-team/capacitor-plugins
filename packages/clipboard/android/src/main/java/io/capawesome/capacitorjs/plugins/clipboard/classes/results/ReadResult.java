package io.capawesome.capacitorjs.plugins.clipboard.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.clipboard.classes.ClipboardContentType;
import io.capawesome.capacitorjs.plugins.clipboard.interfaces.Result;

public class ReadResult implements Result {

    @NonNull
    private final ClipboardContentType type;

    @NonNull
    private final String value;

    public ReadResult(@NonNull ClipboardContentType type, @NonNull String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("type", type.getValue());
        result.put("value", value);
        return result;
    }
}
