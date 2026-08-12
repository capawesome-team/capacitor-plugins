package io.capawesome.capacitorjs.plugins.grafanafaro.anr;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;

public class AnrWatchdog extends Thread {

    public interface Listener {
        void onAnrDetected(@NonNull Throwable throwable);
    }

    private static final long DEFAULT_TIMEOUT_MS = 5000L;
    private static final long POLL_INTERVAL_MS = 500L;

    @NonNull
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @NonNull
    private final Listener listener;

    @NonNull
    private final AtomicBoolean running = new AtomicBoolean(false);

    private final long timeoutMs;

    public AnrWatchdog(@NonNull Listener listener) {
        this(listener, DEFAULT_TIMEOUT_MS);
    }

    public AnrWatchdog(@NonNull Listener listener, long timeoutMs) {
        super("grafana-faro-anr-watchdog");
        setDaemon(true);
        this.listener = listener;
        this.timeoutMs = timeoutMs;
    }

    @Override
    public void run() {
        boolean reported = false;
        while (running.get() && !isInterrupted()) {
            AtomicBoolean ack = new AtomicBoolean(false);
            mainHandler.post(() -> ack.set(true));
            try {
                Thread.sleep(timeoutMs);
            } catch (InterruptedException e) {
                return;
            }
            if (!ack.get()) {
                if (!reported) {
                    reported = true;
                    Throwable throwable = buildMainThreadThrowable();
                    listener.onAnrDetected(throwable);
                }
            } else {
                reported = false;
            }
            try {
                Thread.sleep(POLL_INTERVAL_MS);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void startMonitoring() {
        if (running.compareAndSet(false, true)) {
            start();
        }
    }

    public void stopMonitoring() {
        running.set(false);
        interrupt();
    }

    @NonNull
    private Throwable buildMainThreadThrowable() {
        Thread mainThread = Looper.getMainLooper().getThread();
        Throwable throwable = new Throwable("Application Not Responding (main thread blocked >" + timeoutMs + "ms)");
        StackTraceElement[] stack = mainThread.getStackTrace();
        throwable.setStackTrace(stack != null ? stack : new StackTraceElement[0]);
        return throwable;
    }
}
