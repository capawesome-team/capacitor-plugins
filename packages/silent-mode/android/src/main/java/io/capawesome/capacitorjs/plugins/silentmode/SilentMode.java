package io.capawesome.capacitorjs.plugins.silentmode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import io.capawesome.capacitorjs.plugins.silentmode.classes.events.SilentModeChangeEvent;
import io.capawesome.capacitorjs.plugins.silentmode.classes.results.GetRingerModeResult;
import io.capawesome.capacitorjs.plugins.silentmode.classes.results.IsSilentResult;
import io.capawesome.capacitorjs.plugins.silentmode.interfaces.NonEmptyResultCallback;

public class SilentMode {

    @NonNull
    private final SilentModePlugin plugin;

    @NonNull
    private final AudioManager audioManager;

    private boolean isObserving = false;

    private boolean lastSilent = false;

    private final BroadcastReceiver ringerModeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            handleRingerModeChanged();
        }
    };

    public SilentMode(@NonNull SilentModePlugin plugin) {
        this.plugin = plugin;
        this.audioManager = (AudioManager) plugin.getContext().getSystemService(Context.AUDIO_SERVICE);
    }

    public void getRingerMode(@NonNull NonEmptyResultCallback<GetRingerModeResult> callback) {
        String mode = mapRingerMode(audioManager.getRingerMode());
        callback.success(new GetRingerModeResult(mode));
    }

    public void isSilent(@NonNull NonEmptyResultCallback<IsSilentResult> callback) {
        callback.success(new IsSilentResult(isSilent()));
    }

    public void startObserving() {
        if (isObserving) {
            return;
        }
        lastSilent = isSilent();
        IntentFilter filter = new IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION);
        ContextCompat.registerReceiver(plugin.getContext(), ringerModeReceiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED);
        isObserving = true;
    }

    public void stopObserving() {
        if (!isObserving) {
            return;
        }
        plugin.getContext().unregisterReceiver(ringerModeReceiver);
        isObserving = false;
    }

    private void handleRingerModeChanged() {
        boolean silent = isSilent();
        if (silent == lastSilent) {
            return;
        }
        lastSilent = silent;
        plugin.notifySilentModeChangeListeners(new SilentModeChangeEvent(silent));
    }

    private boolean isSilent() {
        return audioManager.getRingerMode() != AudioManager.RINGER_MODE_NORMAL;
    }

    @NonNull
    private String mapRingerMode(int ringerMode) {
        switch (ringerMode) {
            case AudioManager.RINGER_MODE_SILENT:
                return "silent";
            case AudioManager.RINGER_MODE_VIBRATE:
                return "vibrate";
            default:
                return "normal";
        }
    }
}
