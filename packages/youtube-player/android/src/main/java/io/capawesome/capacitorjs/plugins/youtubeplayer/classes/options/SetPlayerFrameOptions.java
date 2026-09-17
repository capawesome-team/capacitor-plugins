package io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.youtubeplayer.YoutubePlayerHelper;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.PlayerFrame;

public class SetPlayerFrameOptions {

    @NonNull
    private final PlayerFrame frame;

    @NonNull
    private final String id;

    public SetPlayerFrameOptions(@NonNull PluginCall call) throws Exception {
        this.frame = PlayerFrame.getFrameFromCall(call);
        this.id = YoutubePlayerHelper.getPlayerIdFromCall(call);
    }

    @NonNull
    public PlayerFrame getFrame() {
        return frame;
    }

    @NonNull
    public String getId() {
        return id;
    }
}
