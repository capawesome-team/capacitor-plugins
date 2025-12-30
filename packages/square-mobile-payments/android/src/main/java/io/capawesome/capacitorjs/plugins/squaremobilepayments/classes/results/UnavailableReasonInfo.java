package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.interfaces.Result;
import org.json.JSONObject;

public class UnavailableReasonInfo implements Result {

    @NonNull
    private final String reason;

    @Nullable
    private final String message;

    public UnavailableReasonInfo(@NonNull String reason, @Nullable String message) {
        this.reason = reason;
        this.message = message;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("reason", reason);
        result.put("message", message == null ? JSONObject.NULL : message);
        return result;
    }
}
