package io.capawesome.capacitorjs.plugins.liveupdate.classes.options;

import androidx.annotation.NonNull;

public class SetBundleOptions {

    @NonNull
    private String bundleId;

    public SetBundleOptions(@NonNull String bundleId) {
        this.bundleId = bundleId;
    }

    @NonNull
    public String getBundleId() {
        return bundleId;
    }
}
