package io.capawesome.capacitorjs.plugins.liveupdate.classes.api;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class GetLatestBundleResponse {

    private String bundleId;

    @Nullable
    private String checksum;

    @Nullable
    private List<ManifestItem> manifest;

    @Nullable
    private String signature;

    private String url;

    public GetLatestBundleResponse(JSONObject responseJson) {
        this.bundleId = responseJson.optString("bundleId");
        String checksum = responseJson.optString("checksum", "null");
        if (checksum.equals("null")) {
            this.checksum = null;
        } else {
            this.checksum = checksum;
        }
        JSONArray manifestJson = responseJson.optJSONArray("manifest");
        if (manifestJson == null) {
            this.manifest = null;
        } else {
            for (int i = 0; i < manifestJson.length(); i++) {
                JSONObject manifestItemJson = manifestJson.optJSONObject(i);
                if (manifestItemJson != null) {
                    manifest.add(new ManifestItem(manifestItemJson));
                }
            }
        }
        String signature = responseJson.optString("signature", "null");
        if (signature.equals("null")) {
            this.signature = null;
        } else {
            this.signature = signature;
        }
        this.url = responseJson.optString("url");
    }

    public String getBundleId() {
        return bundleId;
    }

    @Nullable
    public String getChecksum() {
        return checksum;
    }

    @Nullable
    public List<ManifestItem> getManifest() {
        return manifest;
    }

    @Nullable
    public String getSignature() {
        return signature;
    }

    public String getUrl() {
        return url;
    }
}
