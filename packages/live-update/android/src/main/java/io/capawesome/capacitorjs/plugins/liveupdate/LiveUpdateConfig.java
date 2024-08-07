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

    @Nullable
    private String location = null;

    @Nullable
    private String publicKey = null;

    private int readyTimeout = 10000;
    private boolean resetOnUpdate = true;

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

    @Nullable
    public String getLocation() {
        return location;
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

    public void setLocation(@Nullable String location) {
        this.location = location;
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
}
