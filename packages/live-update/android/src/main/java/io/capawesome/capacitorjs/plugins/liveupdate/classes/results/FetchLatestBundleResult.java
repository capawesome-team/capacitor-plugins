package io.capawesome.capacitorjs.plugins.liveupdate.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.DownloadUrl;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.api.CreateCheckResponse;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.api.CreateCheckResponseBundle;
import io.capawesome.capacitorjs.plugins.liveupdate.enums.ArtifactType;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;
import org.json.JSONObject;

public class FetchLatestBundleResult implements Result {

    @Nullable
    private final ArtifactType artifactType;

    @Nullable
    private final String bundleId;

    @Nullable
    private final String channel;

    @Nullable
    private final String checksum;

    @Nullable
    private final JSONObject customProperties;

    @Nullable
    private final String downloadUrl;

    @Nullable
    private final String signature;

    public FetchLatestBundleResult(@NonNull CreateCheckResponse response) {
        CreateCheckResponseBundle bundle = response.getBundle();
        this.artifactType = bundle == null ? null : bundle.getArtifactType();
        this.bundleId = bundle == null ? null : bundle.getId();
        this.channel = response.getChannelName();
        this.checksum = bundle == null ? null : bundle.getChecksum();
        this.customProperties = bundle == null ? null : bundle.getCustomProperties();
        this.downloadUrl = bundle == null ? null : DownloadUrl.fromBundle(bundle).toString();
        this.signature = bundle == null ? null : bundle.getSignature();
    }

    @Nullable
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    @Nullable
    public String getBundleId() {
        return bundleId;
    }

    @Nullable
    public String getChecksum() {
        return checksum;
    }

    @Nullable
    public JSONObject getCustomProperties() {
        return customProperties;
    }

    @Nullable
    public String getDownloadUrl() {
        return downloadUrl;
    }

    @Nullable
    public String getSignature() {
        return signature;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        if (artifactType != null) {
            result.put("artifactType", artifactType.getValue());
        }
        result.put("bundleId", bundleId == null ? JSONObject.NULL : bundleId);
        if (channel != null) {
            result.put("channel", channel);
        }
        if (checksum != null) {
            result.put("checksum", checksum);
        }
        if (customProperties != null) {
            result.put("customProperties", customProperties);
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
