package io.capawesome.capacitorjs.plugins.mapslauncher.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.mapslauncher.interfaces.Result;
import org.json.JSONObject;

public class GetDefaultAppResult implements Result {

    @Nullable
    private final String app;

    public GetDefaultAppResult(@Nullable String app) {
        this.app = app;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("app", app == null ? JSONObject.NULL : app);
        return result;
    }
}
