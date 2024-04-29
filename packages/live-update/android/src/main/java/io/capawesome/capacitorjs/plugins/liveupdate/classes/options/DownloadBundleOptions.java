package io.capawesome.capacitorjs.plugins.liveupdate.classes.options;

import androidx.annotation.NonNull;

public class DownloadBundleOptions {

    @NonNull
    private String bundleId;

    @NonNull
    private String url;

    public DownloadBundleOptions(@NonNull String bundleId, @NonNull String url) {
        this.bundleId = bundleId;
        this.url = url;
    }

    @NonNull
    public String getBundleId() {
        return bundleId;
    }

    @NonNull
    public String getUrl() {
        return url;
    }
}
