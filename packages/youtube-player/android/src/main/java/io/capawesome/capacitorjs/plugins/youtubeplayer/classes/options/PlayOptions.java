package io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.youtubeplayer.YoutubePlayerHelper;

public class PlayOptions {

    @NonNull
    private final String id;

    public PlayOptions(@NonNull PluginCall call) throws Exception {
        this.id = YoutubePlayerHelper.getPlayerIdFromCall(call);
    }

    @NonNull
    public String getId() {
        return id;
    }
}
