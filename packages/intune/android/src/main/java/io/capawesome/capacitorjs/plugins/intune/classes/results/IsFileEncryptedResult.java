package io.capawesome.capacitorjs.plugins.intune.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.intune.interfaces.Result;

public class IsFileEncryptedResult implements Result {

    private final boolean encrypted;

    public IsFileEncryptedResult(boolean encrypted) {
        this.encrypted = encrypted;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("encrypted", encrypted);
        return result;
    }
}
