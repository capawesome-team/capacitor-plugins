package io.capawesome.capacitorjs.plugins.liveupdate.classes.api;

import androidx.annotation.Nullable;
import org.json.JSONObject;

public class GetLatestBundleResponse {

    private String bundleId;

    @Nullable
    private String checksum;

    private String url;

    public GetLatestBundleResponse(JSONObject responseJson) {
        this.bundleId = responseJson.optString("bundleId");
        String checksum = responseJson.optString("checksum", "null");
        if (checksum.equals("null")) {
            this.checksum = null;
        } else {
            this.checksum = checksum;
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

    public String getUrl() {
        return url;
    }
}
