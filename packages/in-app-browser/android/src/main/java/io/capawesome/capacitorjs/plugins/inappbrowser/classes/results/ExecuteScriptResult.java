package io.capawesome.capacitorjs.plugins.inappbrowser.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.inappbrowser.interfaces.Result;
import org.json.JSONObject;

public class ExecuteScriptResult implements Result {

    @Nullable
    private final String result;

    public ExecuteScriptResult(@Nullable String result) {
        this.result = result;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("result", this.result == null ? JSONObject.NULL : this.result);
        return result;
    }
}
