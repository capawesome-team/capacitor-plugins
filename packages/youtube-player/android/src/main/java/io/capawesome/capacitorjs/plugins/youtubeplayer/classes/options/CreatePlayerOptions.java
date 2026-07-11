package io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.PlayerFrame;
import java.util.UUID;

public class CreatePlayerOptions {

    private final boolean autoplay;
    private final boolean ccLoadPolicy;
    private final boolean controls;

    @Nullable
    private final Integer end;

    @NonNull
    private final PlayerFrame frame;

    private final boolean fullscreen;

    @NonNull
    private final String id;

    private final boolean ivLoadPolicy;
    private final boolean mute;
    private final boolean rel;

    @Nullable
    private final Integer start;

    @Nullable
    private final String videoId;

    public CreatePlayerOptions(@NonNull PluginCall call) throws Exception {
        JSObject playerOptions = call.getObject("options", null);
        this.autoplay = playerOptions != null && playerOptions.optBoolean("autoplay", false);
        this.ccLoadPolicy = playerOptions != null && playerOptions.optBoolean("ccLoadPolicy", false);
        this.controls = playerOptions == null || playerOptions.optBoolean("controls", true);
        this.end = CreatePlayerOptions.getSecondsFromObject(playerOptions, "end");
        this.frame = PlayerFrame.getFrameFromCall(call);
        this.fullscreen = playerOptions != null && playerOptions.optBoolean("fullscreen", false);
        this.id = CreatePlayerOptions.getIdFromCall(call);
        this.ivLoadPolicy = playerOptions != null && playerOptions.optBoolean("ivLoadPolicy", false);
        this.mute = playerOptions != null && playerOptions.optBoolean("mute", false);
        this.rel = playerOptions != null && playerOptions.optBoolean("rel", false);
        this.start = CreatePlayerOptions.getSecondsFromObject(playerOptions, "start");
        this.videoId = call.getString("videoId");
    }

    public boolean getAutoplay() {
        return autoplay;
    }

    public boolean getCcLoadPolicy() {
        return ccLoadPolicy;
    }

    public boolean getControls() {
        return controls;
    }

    @Nullable
    public Integer getEnd() {
        return end;
    }

    @NonNull
    public PlayerFrame getFrame() {
        return frame;
    }

    public boolean getFullscreen() {
        return fullscreen;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public boolean getIvLoadPolicy() {
        return ivLoadPolicy;
    }

    public boolean getMute() {
        return mute;
    }

    public boolean getRel() {
        return rel;
    }

    @Nullable
    public Integer getStart() {
        return start;
    }

    @Nullable
    public String getVideoId() {
        return videoId;
    }

    @NonNull
    private static String getIdFromCall(@NonNull PluginCall call) {
        String id = call.getString("id");
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        return id;
    }

    @Nullable
    private static Integer getSecondsFromObject(@Nullable JSObject object, @NonNull String key) {
        if (object == null || !object.has(key)) {
            return null;
        }
        return object.optInt(key);
    }
}
