package io.capawesome.capacitorjs.plugins.liveupdate.classes.options;

import androidx.annotation.NonNull;

public class SetChannelOptions {

    @NonNull
    private String channel;

    public SetChannelOptions(@NonNull String channel) {
        this.channel = channel;
    }

    @NonNull
    public String getChannel() {
        return channel;
    }
}
