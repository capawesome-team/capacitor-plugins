package io.capawesome.capacitorjs.plugins.grafanafaro.crash;

import android.app.ActivityManager;
import android.app.ApplicationExitInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import java.util.ArrayList;
import java.util.List;

public class CrashReporter {

    public interface Listener {
        void onPreviousCrashDetected(@NonNull String type, @NonNull String message, @Nullable String stacktrace, long timestampMs);
    }

    private static final String PREFS_NAME = "io.capawesome.capacitorjs.plugins.grafanafaro";
    private static final String KEY_LAST_REPORTED_TIMESTAMP = "last_reported_exit_timestamp";
    private static final String TAG = "GrafanaFaro";

    @NonNull
    private final Context context;

    @Nullable
    private Thread.UncaughtExceptionHandler previousHandler;

    public CrashReporter(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    public void install() {
        previousHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            // Crash records are surfaced via ApplicationExitInfo on the next
            // launch (Android 11+). Chain to the previous handler so other
            // crash reporters / the runtime can still take action.
            if (previousHandler != null) {
                previousHandler.uncaughtException(thread, throwable);
            }
        });
    }

    public List<PreviousCrash> collectPreviousCrashes() {
        List<PreviousCrash> crashes = new ArrayList<>();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            return crashes;
        }
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return crashes;
        }
        long lastReported = readLastReportedTimestamp();
        long maxTimestamp = lastReported;
        try {
            List<ApplicationExitInfo> infos = manager.getHistoricalProcessExitReasons(null, 0, 0);
            for (ApplicationExitInfo info : infos) {
                if (info.getTimestamp() <= lastReported) {
                    continue;
                }
                PreviousCrash crash = mapExitInfo(info);
                if (crash != null) {
                    crashes.add(crash);
                }
                if (info.getTimestamp() > maxTimestamp) {
                    maxTimestamp = info.getTimestamp();
                }
            }
        } catch (Exception exception) {
            Logger.warn(TAG, "Failed to collect previous crashes: " + exception.getMessage());
        }
        if (maxTimestamp > lastReported) {
            writeLastReportedTimestamp(maxTimestamp);
        }
        return crashes;
    }

    @Nullable
    private PreviousCrash mapExitInfo(@NonNull ApplicationExitInfo info) {
        String type;
        switch (info.getReason()) {
            case ApplicationExitInfo.REASON_CRASH:
                type = "Crash";
                break;
            case ApplicationExitInfo.REASON_CRASH_NATIVE:
                type = "NativeCrash";
                break;
            case ApplicationExitInfo.REASON_ANR:
                type = "ANR";
                break;
            default:
                return null;
        }
        String description = info.getDescription();
        String message = description != null ? description : type;
        return new PreviousCrash(type, message, null, info.getTimestamp());
    }

    private long readLastReportedTimestamp() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getLong(KEY_LAST_REPORTED_TIMESTAMP, 0L);
    }

    private void writeLastReportedTimestamp(long timestamp) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putLong(KEY_LAST_REPORTED_TIMESTAMP, timestamp).apply();
    }

    public static class PreviousCrash {

        @NonNull
        public final String type;

        @NonNull
        public final String message;

        @Nullable
        public final String stacktrace;

        public final long timestampMs;

        public PreviousCrash(@NonNull String type, @NonNull String message, @Nullable String stacktrace, long timestampMs) {
            this.message = message;
            this.stacktrace = stacktrace;
            this.timestampMs = timestampMs;
            this.type = type;
        }
    }
}
