package io.capawesome.capacitorjs.plugins.volume;

import android.media.AudioManager;
import androidx.annotation.NonNull;
import io.capawesome.capacitorjs.plugins.volume.classes.CustomExceptions;

public class VolumeHelper {

    public static int mapStreamToStreamType(@NonNull String stream) throws Exception {
        switch (stream) {
            case "alarm":
                return AudioManager.STREAM_ALARM;
            case "music":
                return AudioManager.STREAM_MUSIC;
            case "notification":
                return AudioManager.STREAM_NOTIFICATION;
            case "ring":
                return AudioManager.STREAM_RING;
            case "system":
                return AudioManager.STREAM_SYSTEM;
            case "voiceCall":
                return AudioManager.STREAM_VOICE_CALL;
            default:
                throw CustomExceptions.STREAM_INVALID;
        }
    }
}
