package io.capawesome.capacitorjs.plugins.posthog.classes.options;

import androidx.annotation.NonNull;

public class SetupOptions {
    @NonNull
    private String apiKey;

    @NonNull
    private String host;

    public SetupOptions(@NonNull String apiKey, @NonNull String host) {
        this.apiKey = apiKey;
        this.host = host;
    }

    @NonNull
    public String getApiKey() {
        return apiKey;
    }

    @NonNull
    public String getHost() {
        return host;
    }
}
