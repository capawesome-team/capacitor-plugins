package io.capawesome.capacitorjs.plugins.haptics;

import androidx.annotation.NonNull;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.haptics.classes.CustomException;
import io.capawesome.capacitorjs.plugins.haptics.classes.options.ImpactOptions;
import io.capawesome.capacitorjs.plugins.haptics.classes.options.NotificationOptions;
import io.capawesome.capacitorjs.plugins.haptics.classes.options.PerformAndroidHapticOptions;
import io.capawesome.capacitorjs.plugins.haptics.classes.options.PlayPatternOptions;
import io.capawesome.capacitorjs.plugins.haptics.classes.options.VibrateOptions;
import io.capawesome.capacitorjs.plugins.haptics.classes.results.IsAvailableResult;
import io.capawesome.capacitorjs.plugins.haptics.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.haptics.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.haptics.interfaces.Result;

@CapacitorPlugin(name = "Haptics")
public class HapticsPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "HapticsPlugin";

    private Haptics implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new Haptics(this);
    }

    @PluginMethod
    public void impact(PluginCall call) {
        try {
            ImpactOptions options = new ImpactOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.impact(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isAvailable(PluginCall call) {
        try {
            NonEmptyResultCallback<IsAvailableResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsAvailableResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.isAvailable(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void notification(PluginCall call) {
        try {
            NotificationOptions options = new NotificationOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.notification(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void performAndroidHaptic(PluginCall call) {
        try {
            PerformAndroidHapticOptions options = new PerformAndroidHapticOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.performAndroidHaptic(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void playPattern(PluginCall call) {
        try {
            PlayPatternOptions options = new PlayPatternOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.playPattern(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void selectionChanged(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.selectionChanged(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void selectionEnd(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.selectionEnd(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void selectionStart(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.selectionStart(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void vibrate(PluginCall call) {
        try {
            VibrateOptions options = new VibrateOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.vibrate(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
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
