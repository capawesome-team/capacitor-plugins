package io.capawesome.capacitorjs.plugins.haptics;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.view.HapticFeedbackConstants;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import io.capawesome.capacitorjs.plugins.haptics.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.haptics.classes.options.ImpactOptions;
import io.capawesome.capacitorjs.plugins.haptics.classes.options.NotificationOptions;
import io.capawesome.capacitorjs.plugins.haptics.classes.options.PerformAndroidHapticOptions;
import io.capawesome.capacitorjs.plugins.haptics.classes.options.PlayPatternOptions;
import io.capawesome.capacitorjs.plugins.haptics.classes.options.VibrateOptions;
import io.capawesome.capacitorjs.plugins.haptics.classes.results.IsAvailableResult;
import io.capawesome.capacitorjs.plugins.haptics.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.haptics.interfaces.NonEmptyResultCallback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Haptics {

    private static final long TRANSIENT_EVENT_DURATION = 30;

    @NonNull
    private final HapticsPlugin plugin;

    @Nullable
    private final Vibrator vibrator;

    public Haptics(@NonNull HapticsPlugin plugin) {
        this.plugin = plugin;
        this.vibrator = Haptics.getVibrator(plugin.getContext());
    }

    public void impact(@NonNull ImpactOptions options, @NonNull EmptyCallback callback) throws Exception {
        performImpactEffect(options.getStyle());
        callback.success();
    }

    public void isAvailable(@NonNull NonEmptyResultCallback<IsAvailableResult> callback) {
        boolean available = vibrator != null && vibrator.hasVibrator();
        callback.success(new IsAvailableResult(available));
    }

    public void notification(@NonNull NotificationOptions options, @NonNull EmptyCallback callback) throws Exception {
        performNotificationEffect(options.getType());
        callback.success();
    }

    public void performAndroidHaptic(@NonNull PerformAndroidHapticOptions options, @NonNull EmptyCallback callback) throws Exception {
        int constant = getHapticFeedbackConstantForType(options.getType());
        View webView = plugin.getBridge().getWebView();
        webView.post(() -> webView.performHapticFeedback(constant));
        callback.success();
    }

    public void playPattern(@NonNull PlayPatternOptions options, @NonNull EmptyCallback callback) throws Exception {
        if (vibrator != null) {
            List<PlayPatternOptions.Event> events = sortEventsByTime(options.getEvents());
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && supportsComposition(events)) {
                    playComposition(events);
                } else {
                    playTimeline(events);
                }
            } catch (RuntimeException exception) {
                throw CustomExceptions.PATTERN_PLAYBACK_FAILED;
            }
        }
        callback.success();
    }

    public void selectionChanged(@NonNull EmptyCallback callback) {
        performTickEffect();
        callback.success();
    }

    public void selectionEnd(@NonNull EmptyCallback callback) {
        callback.success();
    }

    public void selectionStart(@NonNull EmptyCallback callback) {
        callback.success();
    }

    public void vibrate(@NonNull VibrateOptions options, @NonNull EmptyCallback callback) {
        vibrateOneShot(options.getDuration(), VibrationEffect.DEFAULT_AMPLITUDE);
        callback.success();
    }

    private int getHapticFeedbackConstantForType(@NonNull String type) throws Exception {
        switch (type) {
            case "CLOCK_TICK":
                return HapticFeedbackConstants.CLOCK_TICK;
            case "CONFIRM":
                return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
                    ? HapticFeedbackConstants.CONFIRM
                    : HapticFeedbackConstants.CONTEXT_CLICK;
            case "CONTEXT_CLICK":
                return HapticFeedbackConstants.CONTEXT_CLICK;
            case "KEYBOARD_TAP":
                return HapticFeedbackConstants.KEYBOARD_TAP;
            case "LONG_PRESS":
                return HapticFeedbackConstants.LONG_PRESS;
            case "REJECT":
                return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R ? HapticFeedbackConstants.REJECT : HapticFeedbackConstants.LONG_PRESS;
            case "TOGGLE_OFF":
                return Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE
                    ? HapticFeedbackConstants.TOGGLE_OFF
                    : HapticFeedbackConstants.CLOCK_TICK;
            case "TOGGLE_ON":
                return Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE
                    ? HapticFeedbackConstants.TOGGLE_ON
                    : HapticFeedbackConstants.CLOCK_TICK;
            case "VIRTUAL_KEY":
                return HapticFeedbackConstants.VIRTUAL_KEY;
            default:
                throw CustomExceptions.TYPE_INVALID;
        }
    }

    @Nullable
    private static Vibrator getVibrator(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            VibratorManager vibratorManager = (VibratorManager) context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
            return vibratorManager == null ? null : vibratorManager.getDefaultVibrator();
        }
        return (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    private void performImpactEffect(@NonNull String style) throws Exception {
        switch (style) {
            case "HEAVY":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    vibratePredefined(VibrationEffect.EFFECT_HEAVY_CLICK);
                } else {
                    vibrateOneShot(60, 255);
                }
                break;
            case "LIGHT":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    vibratePredefined(VibrationEffect.EFFECT_TICK);
                } else {
                    vibrateOneShot(20, 100);
                }
                break;
            case "MEDIUM":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    vibratePredefined(VibrationEffect.EFFECT_CLICK);
                } else {
                    vibrateOneShot(40, 180);
                }
                break;
            case "RIGID":
                vibrateOneShot(25, 255);
                break;
            case "SOFT":
                vibrateOneShot(50, 100);
                break;
            default:
                throw CustomExceptions.STYLE_INVALID;
        }
    }

    private void performNotificationEffect(@NonNull String type) throws Exception {
        long[] timings;
        int[] amplitudes;
        switch (type) {
            case "ERROR":
                timings = new long[] { 0, 40, 60, 40, 60, 60 };
                amplitudes = new int[] { 0, 200, 0, 200, 0, 255 };
                break;
            case "SUCCESS":
                timings = new long[] { 0, 40, 80, 60 };
                amplitudes = new int[] { 0, 180, 0, 255 };
                break;
            case "WARNING":
                timings = new long[] { 0, 60, 100, 60 };
                amplitudes = new int[] { 0, 200, 0, 200 };
                break;
            default:
                throw CustomExceptions.TYPE_INVALID;
        }
        vibrateWaveform(timings, amplitudes);
    }

    private void performTickEffect() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            vibratePredefined(VibrationEffect.EFFECT_TICK);
        } else {
            vibrateOneShot(10, 80);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void playComposition(@NonNull List<PlayPatternOptions.Event> sortedEvents) {
        if (vibrator == null) {
            return;
        }
        VibrationEffect.Composition composition = VibrationEffect.startComposition();
        long currentTime = 0;
        for (PlayPatternOptions.Event event : sortedEvents) {
            long startTime = Math.max(event.getTimeMs(), currentTime);
            composition.addPrimitive(VibrationEffect.Composition.PRIMITIVE_CLICK, event.getIntensity(), (int) (startTime - currentTime));
            currentTime = startTime;
        }
        vibrator.vibrate(composition.compose());
    }

    private void playTimeline(@NonNull List<PlayPatternOptions.Event> sortedEvents) {
        long[] timings = new long[sortedEvents.size() * 2];
        int[] amplitudes = new int[sortedEvents.size() * 2];
        long currentTime = 0;
        int index = 0;
        for (PlayPatternOptions.Event event : sortedEvents) {
            long startTime = Math.max(event.getTimeMs(), currentTime);
            long duration = event.isTransient() ? TRANSIENT_EVENT_DURATION : event.getDurationMs();
            timings[index] = startTime - currentTime;
            amplitudes[index] = 0;
            index++;
            timings[index] = duration;
            amplitudes[index] = Math.min(255, Math.round(event.getIntensity() * 255));
            index++;
            currentTime = startTime + duration;
        }
        vibrateWaveform(timings, amplitudes);
    }

    @NonNull
    private List<PlayPatternOptions.Event> sortEventsByTime(@NonNull List<PlayPatternOptions.Event> events) {
        List<PlayPatternOptions.Event> sortedEvents = new ArrayList<>(events);
        Collections.sort(sortedEvents, (a, b) -> Long.compare(a.getTimeMs(), b.getTimeMs()));
        return sortedEvents;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private boolean supportsComposition(@NonNull List<PlayPatternOptions.Event> events) {
        if (vibrator == null) {
            return false;
        }
        for (PlayPatternOptions.Event event : events) {
            if (!event.isTransient()) {
                return false;
            }
        }
        return vibrator.areAllPrimitivesSupported(VibrationEffect.Composition.PRIMITIVE_CLICK);
    }

    private void vibrateOneShot(long duration, int amplitude) {
        if (vibrator == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, amplitude));
        } else {
            vibrator.vibrate(duration);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void vibratePredefined(int effectId) {
        if (vibrator == null) {
            return;
        }
        vibrator.vibrate(VibrationEffect.createPredefined(effectId));
    }

    private void vibrateWaveform(@NonNull long[] timings, @NonNull int[] amplitudes) {
        if (vibrator == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1));
        } else {
            vibrator.vibrate(timings, -1);
        }
    }
}
