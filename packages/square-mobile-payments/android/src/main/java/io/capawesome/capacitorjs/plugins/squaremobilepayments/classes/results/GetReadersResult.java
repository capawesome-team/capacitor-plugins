package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.interfaces.Result;
import java.util.List;

public class GetReadersResult implements Result {

    @NonNull
    private final List<ReaderInfo> readers;

    public GetReadersResult(@NonNull List<ReaderInfo> readers) {
        this.readers = readers;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        JSArray array = new JSArray();
        for (ReaderInfo reader : readers) {
            array.put(reader.toJSObject());
        }
        result.put("readers", array);
        return result;
    }
}
