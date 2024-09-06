package io.capawesome.capacitorjs.plugins.posthog.classes.options;

import androidx.annotation.NonNull;

public class UnregisterOptions {

    @NonNull
    private String key;

    public UnregisterOptions(@NonNull String key) {
        this.key = key;
    }

    @NonNull
    public String getKey() {
        return key;
    }
}
