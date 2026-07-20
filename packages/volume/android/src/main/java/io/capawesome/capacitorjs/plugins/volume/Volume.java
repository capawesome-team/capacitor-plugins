package io.capawesome.capacitorjs.plugins.volume;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.volume.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.volume.classes.events.VolumeButtonPressedEvent;
import io.capawesome.capacitorjs.plugins.volume.classes.events.VolumeButtonReleasedEvent;
import io.capawesome.capacitorjs.plugins.volume.classes.events.VolumeChangeEvent;
import io.capawesome.capacitorjs.plugins.volume.classes.options.GetVolumeOptions;
import io.capawesome.capacitorjs.plugins.volume.classes.options.SetVolumeOptions;
import io.capawesome.capacitorjs.plugins.volume.classes.options.StartWatchingOptions;
import io.capawesome.capacitorjs.plugins.volume.classes.results.GetVolumeResult;
import io.capawesome.capacitorjs.plugins.volume.classes.results.IsWatchingResult;
import io.capawesome.capacitorjs.plugins.volume.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.volume.interfaces.NonEmptyResultCallback;

public class Volume {

    private static final String DIRECTION_DOWN = "down";
    private static final String DIRECTION_UP = "up";

    @NonNull
    private final AudioManager audioManager;

    private int lastVolume = -1;

    @NonNull
    private final View.OnKeyListener onKeyListener = (view, keyCode, event) -> handleKeyEvent(keyCode, event);

    @NonNull
    private final VolumePlugin plugin;

    private boolean suppressVolumeChange = false;

    @Nullable
    private ContentObserver volumeObserver;

    private boolean watching = false;

    public Volume(@NonNull VolumePlugin plugin) {
        this.plugin = plugin;
        this.audioManager = (AudioManager) plugin.getContext().getSystemService(Context.AUDIO_SERVICE);
    }

    public void getVolume(@NonNull GetVolumeOptions options, @NonNull NonEmptyResultCallback<GetVolumeResult> callback) {
        double volume = getNormalizedVolume(options.getStreamType());
        callback.success(new GetVolumeResult(volume));
    }

    public void isWatching(@NonNull NonEmptyResultCallback<IsWatchingResult> callback) {
        callback.success(new IsWatchingResult(watching));
    }

    public void setVolume(@NonNull SetVolumeOptions options, @NonNull EmptyCallback callback) {
        int streamType = options.getStreamType();
        int maxVolume = audioManager.getStreamMaxVolume(streamType);
        int volume = Math.round(options.getVolume() * maxVolume);
        try {
            audioManager.setStreamVolume(streamType, volume, 0);
        } catch (SecurityException exception) {
            callback.error(CustomExceptions.DO_NOT_DISTURB_ACCESS_REQUIRED);
            return;
        }
        callback.success();
    }

    public void startWatching(@NonNull StartWatchingOptions options, @NonNull EmptyCallback callback) {
        if (watching) {
            callback.success();
            return;
        }
        suppressVolumeChange = options.isSuppressVolumeChange();
        lastVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        registerVolumeObserver();
        watching = true;
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                View webView = plugin.getBridge().getWebView();
                webView.setOnKeyListener(onKeyListener);
                webView.requestFocus();
                callback.success();
            });
    }

    public void stopWatching(@Nullable EmptyCallback callback) {
        if (!watching) {
            if (callback != null) {
                callback.success();
            }
            return;
        }
        unregisterVolumeObserver();
        suppressVolumeChange = false;
        watching = false;
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                plugin.getBridge().getWebView().setOnKeyListener(null);
                if (callback != null) {
                    callback.success();
                }
            });
    }

    private double getNormalizedVolume(int streamType) {
        int volume = audioManager.getStreamVolume(streamType);
        int maxVolume = audioManager.getStreamMaxVolume(streamType);
        return volume / (double) maxVolume;
    }

    private boolean handleKeyEvent(int keyCode, @NonNull KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_VOLUME_UP && keyCode != KeyEvent.KEYCODE_VOLUME_DOWN) {
            return false;
        }
        String direction = keyCode == KeyEvent.KEYCODE_VOLUME_UP ? DIRECTION_UP : DIRECTION_DOWN;
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
            plugin.notifyVolumeButtonPressedListeners(new VolumeButtonPressedEvent(direction));
        } else if (event.getAction() == KeyEvent.ACTION_UP) {
            plugin.notifyVolumeButtonReleasedListeners(new VolumeButtonReleasedEvent(direction));
        }
        return suppressVolumeChange;
    }

    private void handleVolumeChanged() {
        int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (volume == lastVolume) {
            return;
        }
        lastVolume = volume;
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        plugin.notifyVolumeChangeListeners(new VolumeChangeEvent(volume / (double) maxVolume));
    }

    private void registerVolumeObserver() {
        ContentObserver volumeObserver = new ContentObserver(new Handler(Looper.getMainLooper())) {
            @Override
            public void onChange(boolean selfChange) {
                handleVolumeChanged();
            }
        };
        plugin.getContext().getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, volumeObserver);
        this.volumeObserver = volumeObserver;
    }

    private void unregisterVolumeObserver() {
        ContentObserver volumeObserver = this.volumeObserver;
        if (volumeObserver != null) {
            plugin.getContext().getContentResolver().unregisterContentObserver(volumeObserver);
            this.volumeObserver = null;
        }
    }
}
