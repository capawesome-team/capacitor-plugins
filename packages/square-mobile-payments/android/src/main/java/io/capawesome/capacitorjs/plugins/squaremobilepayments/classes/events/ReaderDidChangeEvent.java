package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.ReaderInfo;

public class ReaderDidChangeEvent {

    @NonNull
    private final ReaderInfo reader;

    @NonNull
    private final String change;

    public ReaderDidChangeEvent(@NonNull ReaderInfo reader, @NonNull String change) {
        this.reader = reader;
        this.change = change;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("reader", reader.toJSObject());
        result.put("change", change);
        return result;
    }
}
