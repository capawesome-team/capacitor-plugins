package io.capawesome.capacitorjs.plugins.posthog;

import androidx.annotation.Nullable;

public class PosthogConfig {

    @Nullable
    private String apiKey = null;

    private String host = "https://us.i.posthog.com";

    private boolean enableSessionReplay = false;

    private double sessionReplaySampling = 1.0;

    private boolean sessionReplayLinkedFlag = false;

    private boolean enableErrorTracking = false;

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

    public double getSessionReplaySampling() {
        return sessionReplaySampling;
    }

    public boolean getSessionReplayLinkedFlag() {
        return sessionReplayLinkedFlag;
    }

    public boolean getEnableErrorTracking() {
        return enableErrorTracking;
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

    public void setSessionReplaySampling(double sessionReplaySampling) {
        this.sessionReplaySampling = sessionReplaySampling;
    }

    public void setSessionReplayLinkedFlag(boolean sessionReplayLinkedFlag) {
        this.sessionReplayLinkedFlag = sessionReplayLinkedFlag;
    }

    public void setEnableErrorTracking(boolean enableErrorTracking) {
        this.enableErrorTracking = enableErrorTracking;
    }
}
