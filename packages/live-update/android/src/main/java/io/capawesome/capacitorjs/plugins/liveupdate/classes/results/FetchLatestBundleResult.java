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
    private final String checksum;

    @Nullable
    private final String downloadUrl;

    @Nullable
    private final String signature;

    public FetchLatestBundleResult(
        @Nullable ArtifactType artifactType,
        @Nullable String bundleId,
        @Nullable String checksum,
        @Nullable String downloadUrl,
        @Nullable String signature
    ) {
        this.artifactType = artifactType;
        this.bundleId = bundleId;
        this.checksum = checksum;
        this.downloadUrl = downloadUrl;
        this.signature = signature;
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
        if (checksum != null) {
            result.put("checksum", checksum);
        }
        if (downloadUrl != null) {
            result.put("downloadUrl", downloadUrl);
        }
        if (signature != null) {
            result.put("signature", signature);
        }
        return result;
    }
}
