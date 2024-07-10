package io.capawesome.capacitorjs.plugins.liveupdate.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DownloadBundleOptions {

    @NonNull
    private String bundleId;

    @Nullable
    private String checksum;

    @NonNull
    private String url;

    public DownloadBundleOptions(@NonNull String bundleId, @Nullable String checksum, @NonNull String url) {
        this.bundleId = bundleId;
        this.checksum = checksum;
        this.url = url;
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
