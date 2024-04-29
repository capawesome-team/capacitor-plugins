package io.capawesome.capacitorjs.plugins.liveupdate.classes.api;

import androidx.annotation.Nullable;
import org.json.JSONObject;

public class GetLatestBundleResponse {

    private String bundleId;
    private String url;

    public GetLatestBundleResponse(JSONObject responseJson) {
        this.bundleId = responseJson.optString("bundleId");
        this.url = responseJson.optString("url");
    }

    public String getBundleId() {
        return bundleId;
    }

    public String getUrl() {
        return url;
    }
}
