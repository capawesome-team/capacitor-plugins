package io.capawesome.capacitorjs.plugins.liveupdate.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.liveupdate.enums.ArtifactType;

public class DownloadBundleOptions {

    @Nullable
    private final String appId;

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

    public DownloadBundleOptions(
        @NonNull String artifactType,
        @NonNull String bundleId,
        @Nullable String checksum,
        @Nullable String signature,
        @NonNull String url
    ) {
        this(artifactType.equals("manifest") ? ArtifactType.MANIFEST : ArtifactType.ZIP, bundleId, checksum, signature, url);
    }

    public DownloadBundleOptions(
        @NonNull ArtifactType artifactType,
        @NonNull String bundleId,
        @Nullable String checksum,
        @Nullable String signature,
        @NonNull String url
    ) {
        this(null, artifactType, bundleId, checksum, signature, url);
    }

    /**
     * @param appId The app ID under which a failure of the download is recorded, or `null` for the configured app ID.
     */
    public DownloadBundleOptions(
        @Nullable String appId,
        @NonNull ArtifactType artifactType,
        @NonNull String bundleId,
        @Nullable String checksum,
        @Nullable String signature,
        @NonNull String url
    ) {
        this.appId = appId;
        this.artifactType = artifactType;
        this.bundleId = bundleId;
        this.checksum = checksum;
        this.signature = signature;
        this.url = url;
    }

    @Nullable
    public String getAppId() {
        return appId;
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
