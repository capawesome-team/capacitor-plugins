package io.capawesome.capacitorjs.plugins.liveupdate.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class FetchLatestBundleOptions {

    @Nullable
    private final String appId;

    @Nullable
    private final String bundleId;

    @Nullable
    private final String channel;

    public FetchLatestBundleOptions(@NonNull PluginCall call) {
        this(null, null, call.getString("channel", null));
    }

    public FetchLatestBundleOptions(@Nullable String channel) {
        this(null, null, channel);
    }

    public FetchLatestBundleOptions(@Nullable String appId, @Nullable String bundleId, @Nullable String channel) {
        this.appId = appId;
        this.bundleId = bundleId;
        this.channel = channel;
    }

    @Nullable
    public String getAppId() {
        return appId;
    }

    @Nullable
    public String getBundleId() {
        return bundleId;
    }

    @Nullable
    public String getChannel() {
        return channel;
    }
}
