package io.capawesome.capacitorjs.plugins.liveupdate.classes.results;

import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;
import org.json.JSONObject;

public class SyncResult implements Result {

    @Nullable
    private final String nextBundleId;

    @Nullable
    private String skipReason;

    public SyncResult(@Nullable String nextBundleId) {
        this.nextBundleId = nextBundleId;
    }

    /**
     * @param skipReason The reason of a skipped check, or `null` if the check was not skipped.
     */
    public static SyncResult createWithoutNextBundle(@Nullable String skipReason) {
        SyncResult result = new SyncResult(null);
        result.skipReason = skipReason;
        return result;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("nextBundleId", nextBundleId == null ? JSONObject.NULL : nextBundleId);
        if (skipReason != null) {
            result.put("skipReason", skipReason);
        }
        return result;
    }
}
