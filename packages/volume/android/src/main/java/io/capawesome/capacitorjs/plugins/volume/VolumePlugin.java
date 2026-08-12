package io.capawesome.capacitorjs.plugins.volume;

import androidx.annotation.NonNull;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.volume.classes.CustomException;
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
import io.capawesome.capacitorjs.plugins.volume.interfaces.Result;

@CapacitorPlugin(name = "Volume")
public class VolumePlugin extends Plugin {

    public static final String EVENT_VOLUME_BUTTON_PRESSED = "volumeButtonPressed";
    public static final String EVENT_VOLUME_BUTTON_RELEASED = "volumeButtonReleased";
    public static final String EVENT_VOLUME_CHANGE = "volumeChange";
    public static final String TAG = "VolumePlugin";

    private static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private Volume implementation;

    @PluginMethod
    public void getVolume(PluginCall call) {
        try {
            GetVolumeOptions options = new GetVolumeOptions(call);
            NonEmptyResultCallback<GetVolumeResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetVolumeResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getVolume(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isWatching(PluginCall call) {
        try {
            NonEmptyResultCallback<IsWatchingResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsWatchingResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.isWatching(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @Override
    public void load() {
        implementation = new Volume(this);
    }

    public void notifyVolumeButtonPressedListeners(@NonNull VolumeButtonPressedEvent event) {
        notifyListeners(EVENT_VOLUME_BUTTON_PRESSED, event.toJSObject());
    }

    public void notifyVolumeButtonReleasedListeners(@NonNull VolumeButtonReleasedEvent event) {
        notifyListeners(EVENT_VOLUME_BUTTON_RELEASED, event.toJSObject());
    }

    public void notifyVolumeChangeListeners(@NonNull VolumeChangeEvent event) {
        notifyListeners(EVENT_VOLUME_CHANGE, event.toJSObject());
    }

    @PluginMethod
    public void setVolume(PluginCall call) {
        try {
            SetVolumeOptions options = new SetVolumeOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.setVolume(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void startWatching(PluginCall call) {
        try {
            StartWatchingOptions options = new StartWatchingOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.startWatching(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void stopWatching(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.stopWatching(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @Override
    protected void handleOnDestroy() {
        implementation.stopWatching(null);
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

    private void resolveCall(@NonNull PluginCall call, @NonNull Result result) {
        call.resolve(result.toJSObject());
    }
}
