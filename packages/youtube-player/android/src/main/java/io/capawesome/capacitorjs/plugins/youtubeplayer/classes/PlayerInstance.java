package io.capawesome.capacitorjs.plugins.youtubeplayer.classes;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class PlayerInstance {

    @NonNull
    private final YouTubePlayerView view;

    private float currentTime = 0;
    private float duration = 0;

    @Nullable
    private View fullscreenView;

    public PlayerInstance(@NonNull YouTubePlayerView view) {
        this.view = view;
    }

    public float getCurrentTime() {
        return currentTime;
    }

    public float getDuration() {
        return duration;
    }

    @Nullable
    public View getFullscreenView() {
        return fullscreenView;
    }

    @NonNull
    public YouTubePlayerView getView() {
        return view;
    }

    public void setCurrentTime(float currentTime) {
        this.currentTime = currentTime;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void setFullscreenView(@Nullable View fullscreenView) {
        this.fullscreenView = fullscreenView;
    }
}
