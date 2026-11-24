package io.capawesome.capacitorjs.plugins.liveupdate.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.UrlTemplate;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.api.CreateCheckResponseBundle;
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
    private String currentBundleId;

    @Nullable
    private String signature;

    @NonNull
    private String url;

    @Nullable
    private final UrlTemplate urlTemplate;

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
        this.appId = null;
        this.artifactType = artifactType;
        this.bundleId = bundleId;
        this.checksum = checksum;
        this.signature = signature;
        this.url = url;
        this.urlTemplate = null;
    }

    /**
     * @param appId The app ID under which a failure of the download is recorded.
     */
    public DownloadBundleOptions(@NonNull CreateCheckResponseBundle bundle, @NonNull String appId) {
        this.appId = appId;
        this.artifactType = bundle.getArtifactType();
        this.bundleId = bundle.getId();
        this.checksum = bundle.getChecksum();
        this.signature = bundle.getSignature();
        this.url = bundle.getUrl();
        this.urlTemplate = bundle.getUrlTemplate();
    }

    /**
     * @return The app ID under which a failure of the download is recorded, or `null` for the configured app ID.
     */
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

    /**
     * @return The bundle to reuse unchanged files from, or `null` for the current bundle of the WebView.
     */
    @Nullable
    public String getCurrentBundleId() {
        return currentBundleId;
    }

    @Nullable
    public String getSignature() {
        return signature;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    /**
     * @return The template for the files of the bundle, never set for a download from JavaScript.
     */
    @Nullable
    public UrlTemplate getUrlTemplate() {
        return urlTemplate;
    }

    @NonNull
    public DownloadBundleOptions setCurrentBundleId(@Nullable String currentBundleId) {
        this.currentBundleId = currentBundleId;
        return this;
    }
}
