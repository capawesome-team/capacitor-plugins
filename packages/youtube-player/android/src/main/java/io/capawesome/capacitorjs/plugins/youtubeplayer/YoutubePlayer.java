package io.capawesome.capacitorjs.plugins.youtubeplayer;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.PlayerFrame;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.PlayerInstance;
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
import java.util.HashMap;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class YoutubePlayer {

    @NonNull
    private final YoutubePlayerPlugin plugin;

    @NonNull
    private final HashMap<String, PlayerInstance> players = new HashMap<>();

    public YoutubePlayer(@NonNull YoutubePlayerPlugin plugin) {
        this.plugin = plugin;
    }

    public void createPlayer(@NonNull CreatePlayerOptions options, @NonNull NonEmptyResultCallback<CreatePlayerResult> callback) {
        runOnMainThread(() -> {
            try {
                String id = options.getId();
                if (players.containsKey(id)) {
                    callback.error(CustomExceptions.PLAYER_ALREADY_EXISTS);
                    return;
                }
                AppCompatActivity activity = plugin.getActivity();
                WebView webView = plugin.getBridge().getWebView();
                ViewGroup parent = webView == null ? null : (ViewGroup) webView.getParent();
                if (activity == null || parent == null) {
                    callback.error(CustomExceptions.CREATE_FAILED);
                    return;
                }
                YouTubePlayerView view = new YouTubePlayerView(activity);
                view.setEnableAutomaticInitialization(false);
                PlayerInstance instance = new PlayerInstance(view);
                view.addFullscreenListener(createFullscreenListener(id, instance));
                view.initialize(createPlayerListener(id, instance), true, createIFramePlayerOptions(options), options.getVideoId());
                PlayerFrame frame = options.getFrame();
                parent.addView(
                    view,
                    new ViewGroup.LayoutParams(convertToDevicePixels(frame.getWidth()), convertToDevicePixels(frame.getHeight()))
                );
                setViewPosition(view, frame);
                activity.getLifecycle().addObserver(view);
                players.put(id, instance);
                callback.success(new CreatePlayerResult(id));
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void cueVideo(@NonNull CueVideoOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                PlayerInstance instance = getPlayerInstance(options.getId());
                instance
                    .getView()
                    .getYouTubePlayerWhenReady(youTubePlayer -> {
                        youTubePlayer.cueVideo(options.getVideoId(), options.getStartSeconds());
                        callback.success();
                    });
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void destroy() {
        for (PlayerInstance instance : players.values()) {
            destroyPlayerInstance(instance);
        }
        players.clear();
    }

    public void getCurrentTime(@NonNull GetCurrentTimeOptions options, @NonNull NonEmptyResultCallback<GetCurrentTimeResult> callback) {
        runOnMainThread(() -> {
            try {
                PlayerInstance instance = getPlayerInstance(options.getId());
                callback.success(new GetCurrentTimeResult(instance.getCurrentTime()));
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void getDuration(@NonNull GetDurationOptions options, @NonNull NonEmptyResultCallback<GetDurationResult> callback) {
        runOnMainThread(() -> {
            try {
                PlayerInstance instance = getPlayerInstance(options.getId());
                callback.success(new GetDurationResult(instance.getDuration()));
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void loadVideo(@NonNull LoadVideoOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                PlayerInstance instance = getPlayerInstance(options.getId());
                instance
                    .getView()
                    .getYouTubePlayerWhenReady(youTubePlayer -> {
                        youTubePlayer.loadVideo(options.getVideoId(), options.getStartSeconds());
                        callback.success();
                    });
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void mute(@NonNull MuteOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                PlayerInstance instance = getPlayerInstance(options.getId());
                instance
                    .getView()
                    .getYouTubePlayerWhenReady(youTubePlayer -> {
                        youTubePlayer.mute();
                        callback.success();
                    });
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void pause(@NonNull PauseOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                PlayerInstance instance = getPlayerInstance(options.getId());
                instance
                    .getView()
                    .getYouTubePlayerWhenReady(youTubePlayer -> {
                        youTubePlayer.pause();
                        callback.success();
                    });
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void play(@NonNull PlayOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                PlayerInstance instance = getPlayerInstance(options.getId());
                instance
                    .getView()
                    .getYouTubePlayerWhenReady(youTubePlayer -> {
                        youTubePlayer.play();
                        callback.success();
                    });
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void removePlayer(@NonNull RemovePlayerOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                PlayerInstance instance = getPlayerInstance(options.getId());
                destroyPlayerInstance(instance);
                players.remove(options.getId());
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void seekTo(@NonNull SeekToOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                PlayerInstance instance = getPlayerInstance(options.getId());
                instance
                    .getView()
                    .getYouTubePlayerWhenReady(youTubePlayer -> {
                        youTubePlayer.seekTo(options.getSeconds());
                        callback.success();
                    });
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void setPlaybackRate(@NonNull SetPlaybackRateOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                PlayerInstance instance = getPlayerInstance(options.getId());
                PlayerConstants.PlaybackRate playbackRate = YoutubePlayerHelper.mapPlaybackRate(options.getRate());
                instance
                    .getView()
                    .getYouTubePlayerWhenReady(youTubePlayer -> {
                        youTubePlayer.setPlaybackRate(playbackRate);
                        callback.success();
                    });
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void setPlayerFrame(@NonNull SetPlayerFrameOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                PlayerInstance instance = getPlayerInstance(options.getId());
                PlayerFrame frame = options.getFrame();
                YouTubePlayerView view = instance.getView();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = convertToDevicePixels(frame.getWidth());
                layoutParams.height = convertToDevicePixels(frame.getHeight());
                view.setLayoutParams(layoutParams);
                setViewPosition(view, frame);
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void setVolume(@NonNull SetVolumeOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                PlayerInstance instance = getPlayerInstance(options.getId());
                instance
                    .getView()
                    .getYouTubePlayerWhenReady(youTubePlayer -> {
                        youTubePlayer.setVolume(options.getVolume());
                        callback.success();
                    });
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void unmute(@NonNull UnmuteOptions options, @NonNull EmptyCallback callback) {
        runOnMainThread(() -> {
            try {
                PlayerInstance instance = getPlayerInstance(options.getId());
                instance
                    .getView()
                    .getYouTubePlayerWhenReady(youTubePlayer -> {
                        youTubePlayer.unMute();
                        callback.success();
                    });
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    private int convertToDevicePixels(double cssPixels) {
        float density = plugin.getContext().getResources().getDisplayMetrics().density;
        return Math.round((float) cssPixels * density);
    }

    @NonNull
    private FullscreenListener createFullscreenListener(@NonNull String id, @NonNull PlayerInstance instance) {
        return new FullscreenListener() {
            @Override
            public void onEnterFullscreen(@NonNull View fullscreenView, @NonNull Function0<Unit> exitFullscreen) {
                AppCompatActivity activity = plugin.getActivity();
                if (activity == null) {
                    return;
                }
                ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
                decorView.addView(
                    fullscreenView,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                );
                instance.setFullscreenView(fullscreenView);
                plugin.notifyFullscreenChangeListeners(new FullscreenChangeEvent(true, id));
            }

            @Override
            public void onExitFullscreen() {
                View fullscreenView = instance.getFullscreenView();
                if (fullscreenView != null) {
                    ViewGroup parent = (ViewGroup) fullscreenView.getParent();
                    if (parent != null) {
                        parent.removeView(fullscreenView);
                    }
                    instance.setFullscreenView(null);
                }
                plugin.notifyFullscreenChangeListeners(new FullscreenChangeEvent(false, id));
            }
        };
    }

    @NonNull
    private IFramePlayerOptions createIFramePlayerOptions(@NonNull CreatePlayerOptions options) {
        IFramePlayerOptions.Builder builder = new IFramePlayerOptions.Builder(plugin.getContext())
            .autoplay(options.getAutoplay() ? 1 : 0)
            .ccLoadPolicy(options.getCcLoadPolicy() ? 1 : 0)
            .controls(options.getControls() ? 1 : 0)
            .fullscreen(options.getFullscreen() ? 1 : 0)
            .ivLoadPolicy(options.getIvLoadPolicy() ? 1 : 3)
            .mute(options.getMute() ? 1 : 0)
            .rel(options.getRel() ? 1 : 0);
        if (options.getEnd() != null) {
            builder.end(options.getEnd());
        }
        if (options.getStart() != null) {
            builder.start(options.getStart());
        }
        return builder.build();
    }

    @NonNull
    private YouTubePlayerListener createPlayerListener(@NonNull String id, @NonNull PlayerInstance instance) {
        return new AbstractYouTubePlayerListener() {
            @Override
            public void onCurrentSecond(@NonNull YouTubePlayer youTubePlayer, float second) {
                instance.setCurrentTime(second);
                plugin.notifyCurrentTimeChangeListeners(new CurrentTimeChangeEvent(second, id));
            }

            @Override
            public void onError(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerError error) {
                plugin.notifyPlayerErrorListeners(new PlayerErrorEvent(YoutubePlayerHelper.mapPlayerError(error), id));
            }

            @Override
            public void onPlaybackRateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlaybackRate playbackRate) {
                plugin.notifyPlaybackRateChangeListeners(
                    new PlaybackRateChangeEvent(id, YoutubePlayerHelper.mapPlaybackRateToDouble(playbackRate))
                );
            }

            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                plugin.notifyPlayerReadyListeners(new PlayerReadyEvent(id));
            }

            @Override
            public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {
                String mappedState = YoutubePlayerHelper.mapPlayerState(state);
                if (mappedState != null) {
                    plugin.notifyPlayerStateChangeListeners(new PlayerStateChangeEvent(id, mappedState));
                }
            }

            @Override
            public void onVideoDuration(@NonNull YouTubePlayer youTubePlayer, float duration) {
                instance.setDuration(duration);
            }
        };
    }

    private void destroyPlayerInstance(@NonNull PlayerInstance instance) {
        YouTubePlayerView view = instance.getView();
        AppCompatActivity activity = plugin.getActivity();
        if (activity != null) {
            activity.getLifecycle().removeObserver(view);
        }
        View fullscreenView = instance.getFullscreenView();
        if (fullscreenView != null) {
            ViewGroup fullscreenParent = (ViewGroup) fullscreenView.getParent();
            if (fullscreenParent != null) {
                fullscreenParent.removeView(fullscreenView);
            }
            instance.setFullscreenView(null);
        }
        view.release();
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
    }

    @NonNull
    private PlayerInstance getPlayerInstance(@NonNull String id) throws Exception {
        PlayerInstance instance = players.get(id);
        if (instance == null) {
            throw CustomExceptions.PLAYER_ID_INVALID;
        }
        return instance;
    }

    private void runOnMainThread(@NonNull Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    private void setViewPosition(@NonNull YouTubePlayerView view, @NonNull PlayerFrame frame) {
        WebView webView = plugin.getBridge().getWebView();
        int offsetX = webView == null ? 0 : webView.getLeft();
        int offsetY = webView == null ? 0 : webView.getTop();
        view.setX(offsetX + convertToDevicePixels(frame.getX()));
        view.setY(offsetY + convertToDevicePixels(frame.getY()));
    }
}
