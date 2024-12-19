package io.capawesome.capacitorjs.plugins.liveupdate.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class FetchLatestBundleOptions {

    @Nullable
    private final String channel;

    public FetchLatestBundleOptions(@NonNull PluginCall call) {
        this.channel = call.getString("channel", null);
    }

    public FetchLatestBundleOptions(@Nullable String channel) {
        this.channel = channel;
    }

    @Nullable
    public String getChannel() {
        return channel;
    }
}
