package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.ReaderInfo;

public class ReaderWasRemovedEvent {

    @NonNull
    private final ReaderInfo reader;

    public ReaderWasRemovedEvent(@NonNull ReaderInfo reader) {
        this.reader = reader;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("reader", reader.toJSObject());
        return result;
    }
}
