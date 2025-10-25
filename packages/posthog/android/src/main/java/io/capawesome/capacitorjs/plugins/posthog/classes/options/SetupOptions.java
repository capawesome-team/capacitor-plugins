package io.capawesome.capacitorjs.plugins.posthog.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Map;

public class SetupOptions {

    @NonNull
    private String apiKey;

    @NonNull
    private String host;

    @Nullable
    private Map<String, Object> config;

    public SetupOptions(@NonNull String apiKey, @NonNull String host) {
        this.apiKey = apiKey;
        this.host = host;
        this.config = null;
    }

    public SetupOptions(@NonNull String apiKey, @NonNull String host, @Nullable Map<String, Object> config) {
        this.apiKey = apiKey;
        this.host = host;
        this.config = config;
    }

    @NonNull
    public String getApiKey() {
        return apiKey;
    }

    @NonNull
    public String getHost() {
        return host;
    }

    @Nullable
    public Map<String, Object> getConfig() {
        return config;
    }
}
