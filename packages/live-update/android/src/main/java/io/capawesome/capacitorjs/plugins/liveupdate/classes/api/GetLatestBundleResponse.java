package io.capawesome.capacitorjs.plugins.liveupdate.classes.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.capawesome.capacitorjs.plugins.liveupdate.enums.ArtifactType;

public class GetLatestBundleResponse {

    @NonNull
    private ArtifactType artifactType;

    @NonNull
    private String downloadUrl;

    @NonNull
    private String id;

    public GetLatestBundleResponse(JSONObject responseJson) {
        String artifactType = responseJson.optString("artifactType");
        if (artifactType.equals("manifest")) {
            this.artifactType = ArtifactType.MANIFEST;
        } else {
            this.artifactType = ArtifactType.ZIP;
        }
        this.downloadUrl = responseJson.optString("downloadUrl");
        this.id = responseJson.optString("id");
    }

    public ArtifactType getArtifactType() {
        return artifactType;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getId() {
        return id;
    }
}