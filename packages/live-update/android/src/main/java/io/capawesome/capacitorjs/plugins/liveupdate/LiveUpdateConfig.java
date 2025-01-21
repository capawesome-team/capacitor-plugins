package io.capawesome.capacitorjs.plugins.liveupdate;

import androidx.annotation.Nullable;
import java.security.Key;

public class LiveUpdateConfig {

    @Nullable
    private String appId = null;

    private boolean autoDeleteBundles = false;

    @Nullable
    private String defaultChannel = null;

    private boolean enabled = true;
    private int httpTimeout = 60000;

    @Nullable
    private String publicKey = null;

    private int readyTimeout = 0;
    private boolean resetOnUpdate = true;
    private String serverDomain = "api.cloud.capawesome.io";

    @Nullable
    public String getAppId() {
        return appId;
    }

    public boolean getAutoDeleteBundles() {
        return autoDeleteBundles;
    }

    @Nullable
    public String getDefaultChannel() {
        return defaultChannel;
    }

    public boolean getEnabled() {
        return enabled;
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

    public boolean getResetOnUpdate() {
        return resetOnUpdate;
    }

    public String getServerDomain() {
        return serverDomain;
    }

    public void setAppId(@Nullable String appId) {
        this.appId = appId;
    }

    public void setAutoDeleteBundles(boolean autoDeleteBundles) {
        this.autoDeleteBundles = autoDeleteBundles;
    }

    public void setDefaultChannel(@Nullable String defaultChannel) {
        this.defaultChannel = defaultChannel;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public void setResetOnUpdate(boolean resetOnUpdate) {
        this.resetOnUpdate = resetOnUpdate;
    }

    public void setServerDomain(String serverDomain) {
        this.serverDomain = serverDomain;
    }
}
