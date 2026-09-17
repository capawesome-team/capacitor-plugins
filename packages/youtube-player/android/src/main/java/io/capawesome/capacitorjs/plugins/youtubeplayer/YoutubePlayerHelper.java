package io.capawesome.capacitorjs.plugins.youtubeplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.CustomExceptions;

public class YoutubePlayerHelper {

    @NonNull
    public static String getPlayerIdFromCall(@NonNull PluginCall call) throws Exception {
        String id = call.getString("id");
        if (id == null) {
            throw CustomExceptions.PLAYER_ID_MISSING;
        }
        return id;
    }

    @NonNull
    public static PlayerConstants.PlaybackRate mapPlaybackRate(double rate) throws Exception {
        if (rate == 0.25) {
            return PlayerConstants.PlaybackRate.RATE_0_25;
        } else if (rate == 0.5) {
            return PlayerConstants.PlaybackRate.RATE_0_5;
        } else if (rate == 0.75) {
            return PlayerConstants.PlaybackRate.RATE_0_75;
        } else if (rate == 1) {
            return PlayerConstants.PlaybackRate.RATE_1;
        } else if (rate == 1.25) {
            return PlayerConstants.PlaybackRate.RATE_1_25;
        } else if (rate == 1.5) {
            return PlayerConstants.PlaybackRate.RATE_1_5;
        } else if (rate == 1.75) {
            return PlayerConstants.PlaybackRate.RATE_1_75;
        } else if (rate == 2) {
            return PlayerConstants.PlaybackRate.RATE_2;
        } else {
            throw CustomExceptions.RATE_INVALID;
        }
    }

    public static double mapPlaybackRateToDouble(@NonNull PlayerConstants.PlaybackRate playbackRate) {
        switch (playbackRate) {
            case RATE_0_25:
                return 0.25;
            case RATE_0_5:
                return 0.5;
            case RATE_0_75:
                return 0.75;
            case RATE_1_25:
                return 1.25;
            case RATE_1_5:
                return 1.5;
            case RATE_1_75:
                return 1.75;
            case RATE_2:
                return 2;
            default:
                return 1;
        }
    }

    @NonNull
    public static String mapPlayerError(@NonNull PlayerConstants.PlayerError error) {
        switch (error) {
            case INVALID_PARAMETER_IN_REQUEST:
                return "invalid-parameter";
            case HTML_5_PLAYER:
                return "html5-error";
            case VIDEO_NOT_FOUND:
                return "video-not-found";
            case VIDEO_NOT_PLAYABLE_IN_EMBEDDED_PLAYER:
                return "not-embeddable";
            default:
                return "unknown";
        }
    }

    @Nullable
    public static String mapPlayerState(@NonNull PlayerConstants.PlayerState state) {
        switch (state) {
            case UNSTARTED:
                return "unstarted";
            case ENDED:
                return "ended";
            case PLAYING:
                return "playing";
            case PAUSED:
                return "paused";
            case BUFFERING:
                return "buffering";
            case VIDEO_CUED:
                return "cued";
            default:
                return null;
        }
    }
}
