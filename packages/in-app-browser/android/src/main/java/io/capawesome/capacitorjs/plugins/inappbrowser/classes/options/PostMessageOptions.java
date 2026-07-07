package io.capawesome.capacitorjs.plugins.inappbrowser.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.CustomExceptions;

public class PostMessageOptions {

    @NonNull
    private final JSObject data;

    public PostMessageOptions(@NonNull PluginCall call) throws Exception {
        this.data = PostMessageOptions.getDataFromCall(call);
    }

    @NonNull
    public JSObject getData() {
        return data;
    }

    @NonNull
    private static JSObject getDataFromCall(@NonNull PluginCall call) throws Exception {
        JSObject data = call.getObject("data");
        if (data == null) {
            throw CustomExceptions.DATA_MISSING;
        }
        return data;
    }
}
