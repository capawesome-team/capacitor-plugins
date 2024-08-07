package io.capawesome.capacitorjs.plugins.liveupdate.classes.results;

import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.liveupdate.LiveUpdate;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;
import org.json.JSONObject;

public class ReadyResult implements Result {

    @Nullable
    private String currentBundleId;

    @Nullable
    private String previousBundleId;

    private boolean rollback;

    public ReadyResult(@Nullable String currentBundleId, @Nullable String previousBundleId, boolean rollback) {
        this.currentBundleId = currentBundleId;
        this.previousBundleId = previousBundleId;
        this.rollback = rollback;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("currentBundleId", currentBundleId.equals(LiveUpdate.DEFAULT_WEB_ASSET_DIR) ? JSONObject.NULL : currentBundleId);
        result.put("previousBundleId", previousBundleId.equals(LiveUpdate.DEFAULT_WEB_ASSET_DIR) ? JSONObject.NULL : previousBundleId);
        result.put("rollback", rollback);
        return result;
    }
}
