package io.capawesome.capacitorjs.plugins.volume;

import android.media.AudioManager;
import androidx.annotation.NonNull;
import io.capawesome.capacitorjs.plugins.volume.classes.CustomExceptions;

public class VolumeHelper {

    public static int mapStreamToStreamType(@NonNull String stream) throws Exception {
        switch (stream) {
            case "ALARM":
                return AudioManager.STREAM_ALARM;
            case "MUSIC":
                return AudioManager.STREAM_MUSIC;
            case "NOTIFICATION":
                return AudioManager.STREAM_NOTIFICATION;
            case "RING":
                return AudioManager.STREAM_RING;
            case "SYSTEM":
                return AudioManager.STREAM_SYSTEM;
            case "VOICE_CALL":
                return AudioManager.STREAM_VOICE_CALL;
            default:
                throw CustomExceptions.STREAM_INVALID;
        }
    }
}
