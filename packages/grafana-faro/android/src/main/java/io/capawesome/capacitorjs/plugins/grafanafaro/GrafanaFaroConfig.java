package io.capawesome.capacitorjs.plugins.grafanafaro;

import androidx.annotation.Nullable;

public class GrafanaFaroConfig {

    private boolean anrTracking = false;

    @Nullable
    private String apiKey = null;

    @Nullable
    private String appEnvironment = null;

    @Nullable
    private String appName = null;

    @Nullable
    private String appNamespace = null;

    @Nullable
    private String appVersion = null;

    private boolean nativeCrashReporting = false;

    @Nullable
    private String url = null;

    @Nullable
    public String getApiKey() {
        return apiKey;
    }

    @Nullable
    public String getAppEnvironment() {
        return appEnvironment;
    }

    @Nullable
    public String getAppName() {
        return appName;
    }

    @Nullable
    public String getAppNamespace() {
        return appNamespace;
    }

    @Nullable
    public String getAppVersion() {
        return appVersion;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    public boolean isAnrTrackingEnabled() {
        return anrTracking;
    }

    public boolean isNativeCrashReportingEnabled() {
        return nativeCrashReporting;
    }

    public void setAnrTracking(boolean anrTracking) {
        this.anrTracking = anrTracking;
    }

    public void setApiKey(@Nullable String apiKey) {
        this.apiKey = apiKey;
    }

    public void setAppEnvironment(@Nullable String appEnvironment) {
        this.appEnvironment = appEnvironment;
    }

    public void setAppName(@Nullable String appName) {
        this.appName = appName;
    }

    public void setAppNamespace(@Nullable String appNamespace) {
        this.appNamespace = appNamespace;
    }

    public void setAppVersion(@Nullable String appVersion) {
        this.appVersion = appVersion;
    }

    public void setNativeCrashReporting(boolean nativeCrashReporting) {
        this.nativeCrashReporting = nativeCrashReporting;
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }
}
