package io.capawesome.capacitorjs.plugins.posthog;

import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.posthog.classes.options.SessionReplayOptions;

public class PosthogConfig {

    @Nullable
    private String apiKey = null;

    private String host = "https://us.i.posthog.com";

    private boolean enableSessionReplay = false;

    @Nullable
    private SessionReplayOptions sessionReplayConfig = null;

    @Nullable
    public String getApiKey() {
        return apiKey;
    }

    public String getHost() {
        return host;
    }

    public boolean getEnableSessionReplay() {
        return enableSessionReplay;
    }

    @Nullable
    public SessionReplayOptions getSessionReplayConfig() {
        return sessionReplayConfig;
    }

    public void setApiKey(@Nullable String apiKey) {
        this.apiKey = apiKey;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setEnableSessionReplay(boolean enableSessionReplay) {
        this.enableSessionReplay = enableSessionReplay;
    }

    public void setSessionReplayConfig(@Nullable SessionReplayOptions sessionReplayConfig) {
        this.sessionReplayConfig = sessionReplayConfig;
    }
}
