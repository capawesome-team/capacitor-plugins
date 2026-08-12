package io.capawesome.capacitorjs.plugins.pdfviewer.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.pdfviewer.interfaces.Result;

public class PageChangeEvent implements Result {

    private final int page;

    public PageChangeEvent(int page) {
        this.page = page;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("page", page);
        return result;
    }
}
