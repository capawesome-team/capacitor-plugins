package io.capawesome.capacitorjs.plugins.liveupdate.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;
import org.json.JSONObject;

public class GetConfigResult implements Result {

    @Nullable
    private final String appId;

    public GetConfigResult(@Nullable String appId) {
        this.appId = appId;
    }

    @Nullable
    public String getAppId() {
        return appId;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("appId", appId == null ? JSONObject.NULL : appId);
        return result;
    }
}
