package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.events;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import org.json.JSONObject;

public class ReaderPairingDidFailEvent {

    @Nullable
    private final String code;

    @NonNull
    private final String message;

    public ReaderPairingDidFailEvent(@Nullable String code, @NonNull String message) {
        this.code = code;
        this.message = message;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("code", code == null ? JSONObject.NULL : code);
        result.put("message", message);
        return result;
    }
}
