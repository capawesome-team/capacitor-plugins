package io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.youtubeplayer.YoutubePlayerHelper;

public class RemovePlayerOptions {

    @NonNull
    private final String id;

    public RemovePlayerOptions(@NonNull PluginCall call) throws Exception {
        this.id = YoutubePlayerHelper.getPlayerIdFromCall(call);
    }

    @NonNull
    public String getId() {
        return id;
    }
}
