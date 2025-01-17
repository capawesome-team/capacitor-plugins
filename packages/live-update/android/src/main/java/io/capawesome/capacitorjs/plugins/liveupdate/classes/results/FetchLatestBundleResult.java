package io.capawesome.capacitorjs.plugins.liveupdate.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.liveupdate.enums.ArtifactType;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;
import org.json.JSONObject;

public class FetchLatestBundleResult implements Result {

    @Nullable
    private final ArtifactType artifactType;

    @Nullable
    private final String bundleId;

    @Nullable
    private final JSONObject customProperties;

    @Nullable
    private final String downloadUrl;

    public FetchLatestBundleResult(
        @Nullable ArtifactType artifactType,
        @Nullable String bundleId,
        @Nullable JSONObject customProperties,
        @Nullable String downloadUrl
    ) {
        this.artifactType = artifactType;
        this.bundleId = bundleId;
        this.customProperties = customProperties;
        this.downloadUrl = downloadUrl;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        if (artifactType == ArtifactType.MANIFEST) {
            result.put("artifactType", "manifest");
        } else if (artifactType == ArtifactType.ZIP) {
            result.put("artifactType", "zip");
        }
        result.put("bundleId", bundleId == null ? JSONObject.NULL : bundleId);
        if (customProperties != null) {
            result.put("customProperties", customProperties);
        }
        if (downloadUrl != null) {
            result.put("downloadUrl", downloadUrl);
        }
        return result;
    }
}
