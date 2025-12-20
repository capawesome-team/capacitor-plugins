package io.capawesome.capacitorjs.plugins.posthog.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SetupOptions {

    @NonNull
    private String apiKey;

    @NonNull
    private String host;

    @Nullable
    private Boolean enableSessionReplay;

    @Nullable
    private Boolean optOut;

    @Nullable
    private SessionReplayOptions sessionReplayConfig;

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

    public boolean getEnableSessionReplay() {
        return enableSessionReplay != null ? enableSessionReplay : false;
    }

    public void setEnableSessionReplay(boolean enableSessionReplay) {
        this.enableSessionReplay = enableSessionReplay;
    }

    public boolean getOptOut() {
        return optOut != null ? optOut : false;
    }

    public void setOptOut(boolean optOut) {
        this.optOut = optOut;
    }

    @Nullable
    public SessionReplayOptions getSessionReplayConfig() {
        return sessionReplayConfig;
    }

    public void setSessionReplayConfig(@Nullable SessionReplayOptions sessionReplayConfig) {
        this.sessionReplayConfig = sessionReplayConfig;
    }
}
