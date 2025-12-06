package io.capawesome.capacitorjs.plugins.liveupdate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.security.Key;

public class LiveUpdateConfig {

    @Nullable
    private String appId = null;

    private boolean autoBlockRolledBackBundles = false;

    private boolean autoDeleteBundles = false;

    @NonNull
    private String autoUpdateStrategy = "none";

    @Nullable
    private String defaultChannel = null;

    private int httpTimeout = 60000;

    @Nullable
    private String publicKey = null;

    private int readyTimeout = 0;
    private String serverDomain = "api.cloud.capawesome.io";

    @Nullable
    public String getAppId() {
        return appId;
    }

    public boolean getAutoBlockRolledBackBundles() {
        return autoBlockRolledBackBundles;
    }

    public boolean getAutoDeleteBundles() {
        return autoDeleteBundles;
    }

    @NonNull
    public String getAutoUpdateStrategy() {
        return autoUpdateStrategy;
    }

    @Nullable
    public String getDefaultChannel() {
        return defaultChannel;
    }

    public int getHttpTimeout() {
        return httpTimeout;
    }

    @Nullable
    public String getPublicKey() {
        return publicKey;
    }

    public int getReadyTimeout() {
        return readyTimeout;
    }

    public String getServerDomain() {
        return serverDomain;
    }

    public void setAppId(@Nullable String appId) {
        this.appId = appId;
    }

    public void setAutoBlockRolledBackBundles(boolean autoBlockRolledBackBundles) {
        this.autoBlockRolledBackBundles = autoBlockRolledBackBundles;
    }

    public void setAutoDeleteBundles(boolean autoDeleteBundles) {
        this.autoDeleteBundles = autoDeleteBundles;
    }

    public void setAutoUpdateStrategy(@NonNull String autoUpdateStrategy) {
        this.autoUpdateStrategy = autoUpdateStrategy;
    }

    public void setDefaultChannel(@Nullable String defaultChannel) {
        this.defaultChannel = defaultChannel;
    }

    public void setHttpTimeout(int httpTimeout) {
        this.httpTimeout = httpTimeout;
    }

    public void setPublicKey(@Nullable String publicKey) {
        this.publicKey = publicKey;
    }

    public void setReadyTimeout(int readyTimeout) {
        this.readyTimeout = readyTimeout;
    }

    public void setServerDomain(String serverDomain) {
        this.serverDomain = serverDomain;
    }
}
