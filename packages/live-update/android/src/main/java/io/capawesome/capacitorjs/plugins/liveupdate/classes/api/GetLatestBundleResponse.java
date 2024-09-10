package io.capawesome.capacitorjs.plugins.liveupdate.classes.api;

import androidx.annotation.Nullable;
import org.json.JSONObject;

public class GetLatestBundleResponse {

    private String bundleId;

    @Nullable
    private String checksum;

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
    public String getSignature() {
        return signature;
    }

    public String getUrl() {
        return url;
    }
}
