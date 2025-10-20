package io.capawesome.capacitorjs.plugins.liveupdate.classes.results;

import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;
import org.json.JSONObject;

public class GetNextBundleResult implements Result {

    @Nullable
    private String bundleId;

    public GetNextBundleResult(@Nullable String bundleId) {
        this.bundleId = bundleId;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("bundleId", bundleId == null ? JSONObject.NULL : bundleId);
        return result;
    }
}
