package io.capawesome.capacitorjs.plugins.liveupdate.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.capawesome.capacitorjs.plugins.liveupdate.enums.ArtifactType;

public class DownloadBundleOptions {

    @NonNull
    private ArtifactType artifactType;

    @NonNull
    private String bundleId;

    @Nullable
    private String checksum;

    @NonNull
    private String url;

    public DownloadBundleOptions(@NonNull String artifactType, @NonNull String bundleId, @Nullable String checksum, @NonNull String url) {
        if (artifactType.equals("manifest")) {
            this.artifactType = ArtifactType.MANIFEST;
        } else {
            this.artifactType = ArtifactType.ZIP;
        }
        this.bundleId = bundleId;
        this.checksum = checksum;
        this.url = url;
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

    @NonNull
    public String getUrl() {
        return url;
    }
}
