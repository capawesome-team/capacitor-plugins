package io.capawesome.capacitorjs.plugins.liveupdate.classes.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.liveupdate.enums.ArtifactType;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetLatestBundleResponse {

    @NonNull
    private ArtifactType artifactType;

    @NonNull
    private String bundleId;

    @Nullable
    private String checksum;

    @Nullable
    private String signature;

    @NonNull
    private String url;

    public GetLatestBundleResponse(JSONObject responseJson) {
        String artifactType = responseJson.optString("artifactType");
        if (artifactType.equals("manifest")) {
            this.artifactType = ArtifactType.MANIFEST;
        } else {
            this.artifactType = ArtifactType.ZIP;
        }
        this.bundleId = responseJson.optString("bundleId");
        this.checksum = responseJson.optString("checksum");
        this.signature = responseJson.optString("signature");
        this.url = responseJson.optString("url");
    }

    @NonNull
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    @NonNull
    public String getBundleId() {
        return bundleId;
    }

    @Nullable
    public String getChecksum() {
        return checksum;
    }

    @Nullable
    public String getSignature() {
        return signature;
    }

    @NonNull
    public String getUrl() {
        return url;
    }
}
