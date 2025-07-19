package io.capawesome.capacitorjs.plugins.posthog;

import androidx.annotation.Nullable;

public class PosthogConfig {

    @Nullable
    private String apiKey = null;

    private String host = "https://us.i.posthog.com";

    @Nullable
    public String getApiKey() {
        return apiKey;
    }

    public String getHost() {
        return host;
    }

    public void setApiKey(@Nullable String apiKey) {
        this.apiKey = apiKey;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
