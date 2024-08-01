package io.capawesome.capacitorjs.plugins.liveupdate.classes.results;

import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;

import org.json.JSONObject;

import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;

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
        result.put("currentBundleId", currentBundleId == null ? JSONObject.NULL : currentBundleId);
        result.put("previousBundleId", previousBundleId == null ? JSONObject.NULL : previousBundleId);
        result.put("rollback", rollback);
        return result;
    }
}
