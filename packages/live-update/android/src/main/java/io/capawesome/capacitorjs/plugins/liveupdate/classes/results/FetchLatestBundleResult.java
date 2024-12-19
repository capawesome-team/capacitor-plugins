package io.capawesome.capacitorjs.plugins.liveupdate.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;
import org.json.JSONObject;

public class FetchLatestBundleResult implements Result {

    @Nullable
    private final String bundleId;

    @Nullable
    private final String downloadUrl;

    public FetchLatestBundleResult(@Nullable String bundleId, @Nullable String downloadUrl) {
        this.bundleId = bundleId;
        this.downloadUrl = downloadUrl;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("bundleId", bundleId == null ? JSONObject.NULL : bundleId);
        if (downloadUrl != null) {
            result.put("downloadUrl", downloadUrl);
        }
        return result;
    }
}
