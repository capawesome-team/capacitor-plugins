package io.capawesome.capacitorjs.plugins.libsql.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.libsql.interfaces.Result;

public class BeginTransactionResult implements Result {

    @NonNull
    private final String transactionId;

    public BeginTransactionResult(@NonNull String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("transactionId", transactionId);
        return result;
    }
}
