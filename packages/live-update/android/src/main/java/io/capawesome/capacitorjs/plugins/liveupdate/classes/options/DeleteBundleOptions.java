package io.capawesome.capacitorjs.plugins.liveupdate.classes.options;

import androidx.annotation.NonNull;

public class DeleteBundleOptions {

    @NonNull
    private String bundleId;

    public DeleteBundleOptions(@NonNull String bundleId) {
        this.bundleId = bundleId;
    }

    @NonNull
    public String getBundleId() {
        return bundleId;
    }
}
