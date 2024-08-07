package io.capawesome.capacitorjs.plugins.liveupdate.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.liveupdate.LiveUpdate;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;
import org.json.JSONObject;

public class GetBundleResult implements Result {

    @Nullable
    private String bundleId;

    public GetBundleResult(@Nullable String bundleId) {
        this.bundleId = bundleId;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("bundleId", bundleId.equals(LiveUpdate.DEFAULT_WEB_ASSET_DIR) ? JSONObject.NULL : bundleId);
        return result;
    }
}
