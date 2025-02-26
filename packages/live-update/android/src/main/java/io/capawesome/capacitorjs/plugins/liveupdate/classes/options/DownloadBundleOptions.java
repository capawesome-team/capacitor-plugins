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

    @Nullable
    private String signature;

    @NonNull
    private String url;

    public DownloadBundleOptions(@NonNull String artifactType, @NonNull String bundleId, @Nullable String checksum, @Nullable String signature, @NonNull String url) {
        if (artifactType.equals("manifest")) {
            this.artifactType = ArtifactType.MANIFEST;
        } else {
            this.artifactType = ArtifactType.ZIP;
        }
        this.bundleId = bundleId;
        this.checksum = checksum;
        this.signature = signature;
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

    @Nullable
    public String getSignature() {
        return signature;
    }

    @NonNull
    public String getUrl() {
        return url;
    }
}
