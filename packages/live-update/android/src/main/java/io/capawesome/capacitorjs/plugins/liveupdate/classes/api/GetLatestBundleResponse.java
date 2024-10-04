package io.capawesome.capacitorjs.plugins.liveupdate.classes.api;

import androidx.annotation.Nullable;
import org.json.JSONObject;

public class GetLatestBundleResponse {

    @Nullable
    private String bundleId;

    @Nullable
    private String checksum;

    @Nullable
    private String signature;

    @Nullable
    private String url;

    public GetLatestBundleResponse() {
        this.bundleId = null;
        this.checksum = null;
        this.signature = null;
        this.url = null;
    }

    public GetLatestBundleResponse(JSONObject responseJson) {
        this.bundleId = responseJson.optString("bundleId");
        String checksum = responseJson.optString("checksum", "null");
        if (checksum.equals("null")) {
            this.checksum = null;
        } else {
            this.checksum = checksum;
        }
        String signature = responseJson.optString("signature", "null");
        if (signature.equals("null")) {
            this.signature = null;
        } else {
            this.signature = signature;
        }
        this.url = responseJson.optString("url");
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
    public String getSignature() {
        return signature;
    }

    @Nullable
    public String getUrl() {
        return url;
    }
}
