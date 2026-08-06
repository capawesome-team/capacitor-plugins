package io.capawesome.capacitorjs.plugins.liveupdate.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class FetchLatestBundleOptions {

    @Nullable
    private final String appId;

    @Nullable
    private final String channel;

    public FetchLatestBundleOptions(@NonNull PluginCall call) {
        this.appId = null;
        this.channel = call.getString("channel", null);
    }

    public FetchLatestBundleOptions(@Nullable String channel) {
        this.appId = null;
        this.channel = channel;
    }

    public FetchLatestBundleOptions(@Nullable String appId, @Nullable String channel) {
        this.appId = appId;
        this.channel = channel;
    }

    @Nullable
    public String getAppId() {
        return appId;
    }

    @Nullable
    public String getChannel() {
        return channel;
    }
}
