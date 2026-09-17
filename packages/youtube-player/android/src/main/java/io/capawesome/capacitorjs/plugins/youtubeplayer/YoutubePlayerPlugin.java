package io.capawesome.capacitorjs.plugins.youtubeplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.CustomException;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.events.CurrentTimeChangeEvent;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.events.FullscreenChangeEvent;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.events.PlaybackRateChangeEvent;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.events.PlayerErrorEvent;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.events.PlayerReadyEvent;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.events.PlayerStateChangeEvent;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options.CreatePlayerOptions;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options.CueVideoOptions;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options.GetCurrentTimeOptions;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options.GetDurationOptions;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options.LoadVideoOptions;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options.MuteOptions;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options.PauseOptions;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options.PlayOptions;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options.RemovePlayerOptions;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options.SeekToOptions;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options.SetPlaybackRateOptions;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options.SetPlayerFrameOptions;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options.SetVolumeOptions;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options.UnmuteOptions;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.results.CreatePlayerResult;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.results.GetCurrentTimeResult;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.results.GetDurationResult;
import io.capawesome.capacitorjs.plugins.youtubeplayer.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.youtubeplayer.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.youtubeplayer.interfaces.Result;

@CapacitorPlugin(name = "YoutubePlayer")
public class YoutubePlayerPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String EVENT_CURRENT_TIME_CHANGE = "currentTimeChange";
    public static final String EVENT_FULLSCREEN_CHANGE = "fullscreenChange";
    public static final String EVENT_PLAYBACK_RATE_CHANGE = "playbackRateChange";
    public static final String EVENT_PLAYER_ERROR = "playerError";
    public static final String EVENT_PLAYER_READY = "playerReady";
    public static final String EVENT_PLAYER_STATE_CHANGE = "playerStateChange";
    public static final String TAG = "YoutubePlayerPlugin";

    private YoutubePlayer implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new YoutubePlayer(this);
    }

    @PluginMethod
    public void createPlayer(PluginCall call) {
        try {
            CreatePlayerOptions options = new CreatePlayerOptions(call);
            NonEmptyResultCallback<CreatePlayerResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull CreatePlayerResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.createPlayer(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void cueVideo(PluginCall call) {
        try {
            CueVideoOptions options = new CueVideoOptions(call);
            implementation.cueVideo(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getCurrentTime(PluginCall call) {
        try {
            GetCurrentTimeOptions options = new GetCurrentTimeOptions(call);
            NonEmptyResultCallback<GetCurrentTimeResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetCurrentTimeResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getCurrentTime(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getDuration(PluginCall call) {
        try {
            GetDurationOptions options = new GetDurationOptions(call);
            NonEmptyResultCallback<GetDurationResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetDurationResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getDuration(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void loadVideo(PluginCall call) {
        try {
            LoadVideoOptions options = new LoadVideoOptions(call);
            implementation.loadVideo(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void mute(PluginCall call) {
        try {
            MuteOptions options = new MuteOptions(call);
            implementation.mute(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void notifyCurrentTimeChangeListeners(@NonNull CurrentTimeChangeEvent event) {
        notifyListeners(EVENT_CURRENT_TIME_CHANGE, event.toJSObject());
    }

    public void notifyFullscreenChangeListeners(@NonNull FullscreenChangeEvent event) {
        notifyListeners(EVENT_FULLSCREEN_CHANGE, event.toJSObject());
    }

    public void notifyPlaybackRateChangeListeners(@NonNull PlaybackRateChangeEvent event) {
        notifyListeners(EVENT_PLAYBACK_RATE_CHANGE, event.toJSObject());
    }

    public void notifyPlayerErrorListeners(@NonNull PlayerErrorEvent event) {
        notifyListeners(EVENT_PLAYER_ERROR, event.toJSObject());
    }

    public void notifyPlayerReadyListeners(@NonNull PlayerReadyEvent event) {
        notifyListeners(EVENT_PLAYER_READY, event.toJSObject());
    }

    public void notifyPlayerStateChangeListeners(@NonNull PlayerStateChangeEvent event) {
        notifyListeners(EVENT_PLAYER_STATE_CHANGE, event.toJSObject());
    }

    @PluginMethod
    public void pause(PluginCall call) {
        try {
            PauseOptions options = new PauseOptions(call);
            implementation.pause(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void play(PluginCall call) {
        try {
            PlayOptions options = new PlayOptions(call);
            implementation.play(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void removePlayer(PluginCall call) {
        try {
            RemovePlayerOptions options = new RemovePlayerOptions(call);
            implementation.removePlayer(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void seekTo(PluginCall call) {
        try {
            SeekToOptions options = new SeekToOptions(call);
            implementation.seekTo(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setPlaybackRate(PluginCall call) {
        try {
            SetPlaybackRateOptions options = new SetPlaybackRateOptions(call);
            implementation.setPlaybackRate(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setPlayerFrame(PluginCall call) {
        try {
            SetPlayerFrameOptions options = new SetPlayerFrameOptions(call);
            implementation.setPlayerFrame(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setVolume(PluginCall call) {
        try {
            SetVolumeOptions options = new SetVolumeOptions(call);
            implementation.setVolume(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void unmute(PluginCall call) {
        try {
            UnmuteOptions options = new UnmuteOptions(call);
            implementation.unmute(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @Override
    protected void handleOnDestroy() {
        super.handleOnDestroy();
        if (implementation != null) {
            implementation.destroy();
        }
    }

    @NonNull
    private EmptyCallback createEmptyCallback(@NonNull PluginCall call) {
        return new EmptyCallback() {
            @Override
            public void success() {
                resolveCall(call);
            }

            @Override
            public void error(Exception exception) {
                rejectCall(call, exception);
            }
        };
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        String code = null;
        if (exception instanceof CustomException) {
            code = ((CustomException) exception).getCode();
        }
        Logger.error(TAG, message, exception);
        call.reject(message, code);
    }

    private void resolveCall(@NonNull PluginCall call) {
        call.resolve();
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
