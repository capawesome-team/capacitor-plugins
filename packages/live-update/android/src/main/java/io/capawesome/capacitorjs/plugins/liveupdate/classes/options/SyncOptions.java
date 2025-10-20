package io.capawesome.capacitorjs.plugins.liveupdate.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class SyncOptions {

    @Nullable
    private final String channel;

    public SyncOptions(@NonNull PluginCall call) {
        this.channel = call.getString("channel", null);
    }

    @Nullable
    public String getChannel() {
        return channel;
    }
}
