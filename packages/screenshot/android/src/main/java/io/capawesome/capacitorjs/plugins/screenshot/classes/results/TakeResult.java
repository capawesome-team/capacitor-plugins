package io.capawesome.capacitorjs.plugins.screenshot.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.screenshot.classes.interfaces.Result;
import java.io.File;

public class TakeResult implements Result {

    private final String path;

    public TakeResult(@NonNull File file) {
        path = file.getAbsolutePath();
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("uri", path);
        return result;
    }
}
