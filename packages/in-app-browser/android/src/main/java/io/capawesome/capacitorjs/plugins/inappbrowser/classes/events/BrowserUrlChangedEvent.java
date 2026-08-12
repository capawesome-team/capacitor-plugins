package io.capawesome.capacitorjs.plugins.inappbrowser.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.inappbrowser.interfaces.Result;

public class BrowserUrlChangedEvent implements Result {

    @NonNull
    private final String url;

    public BrowserUrlChangedEvent(@NonNull String url) {
        this.url = url;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("url", url);
        return result;
    }
}
