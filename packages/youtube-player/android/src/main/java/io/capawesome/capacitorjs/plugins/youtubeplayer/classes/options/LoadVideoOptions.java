package io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.youtubeplayer.YoutubePlayerHelper;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.CustomExceptions;

public class LoadVideoOptions {

    @NonNull
    private final String id;

    private final float startSeconds;

    @NonNull
    private final String videoId;

    public LoadVideoOptions(@NonNull PluginCall call) throws Exception {
        this.id = YoutubePlayerHelper.getPlayerIdFromCall(call);
        this.startSeconds = call.getDouble("startSeconds", 0.0).floatValue();
        this.videoId = LoadVideoOptions.getVideoIdFromCall(call);
    }

    @NonNull
    public String getId() {
        return id;
    }

    public float getStartSeconds() {
        return startSeconds;
    }

    @NonNull
    public String getVideoId() {
        return videoId;
    }

    @NonNull
    private static String getVideoIdFromCall(@NonNull PluginCall call) throws Exception {
        String videoId = call.getString("videoId");
        if (videoId == null) {
            throw CustomExceptions.VIDEO_ID_MISSING;
        }
        return videoId;
    }
}
