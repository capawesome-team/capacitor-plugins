package io.capawesome.capacitorjs.plugins.inappbrowser.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.inappbrowser.interfaces.Result;

public class BrowserPageNavigationCompletedEvent implements Result {

    @NonNull
    private final String url;

    public BrowserPageNavigationCompletedEvent(@NonNull String url) {
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
