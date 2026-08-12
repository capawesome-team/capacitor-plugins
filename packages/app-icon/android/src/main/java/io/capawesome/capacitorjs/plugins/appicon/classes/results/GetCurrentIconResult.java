package io.capawesome.capacitorjs.plugins.appicon.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.appicon.interfaces.Result;
import org.json.JSONObject;

public class GetCurrentIconResult implements Result {

    @Nullable
    private final String icon;

    public GetCurrentIconResult(@Nullable String icon) {
        this.icon = icon;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("icon", icon == null ? JSONObject.NULL : icon);
        return result;
    }
}
