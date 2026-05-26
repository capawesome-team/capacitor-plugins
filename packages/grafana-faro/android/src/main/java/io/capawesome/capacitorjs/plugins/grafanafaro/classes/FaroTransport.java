package io.capawesome.capacitorjs.plugins.grafanafaro.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class FaroTransport {

    public enum SignalType {
        LOG("logs"),
        EVENT("events"),
        EXCEPTION("exceptions"),
        MEASUREMENT("measurements");

        @NonNull
        private final String bodyKey;

        SignalType(@NonNull String bodyKey) {
            this.bodyKey = bodyKey;
        }

        @NonNull
        public String getBodyKey() {
            return bodyKey;
        }
    }

    private static final String TAG = "GrafanaFaro";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final int ITEM_LIMIT = 50;
    private static final long SEND_TIMEOUT_MS = 250L;
    private static final long REQUEST_TIMEOUT_SECONDS = 10L;

    @NonNull
    private final OkHttpClient client = new OkHttpClient.Builder().callTimeout(REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS).build();

    @NonNull
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(runnable -> {
        Thread thread = new Thread(runnable, "grafana-faro-transport");
        thread.setDaemon(true);
        return thread;
    });

    @Nullable
    private final String apiKey;

    @NonNull
    private final FaroMeta meta;

    @NonNull
    private final Queue<Signal> queue = new LinkedList<>();

    @NonNull
    private final AtomicBoolean paused;

    @NonNull
    private final String url;

    @Nullable
    private ScheduledFuture<?> pendingFlush;

    public FaroTransport(@NonNull String url, @Nullable String apiKey, @NonNull FaroMeta meta, boolean startPaused) {
        this.apiKey = apiKey;
        this.meta = meta;
        this.paused = new AtomicBoolean(startPaused);
        this.url = url;
    }

    public synchronized void enqueue(@NonNull SignalType type, @NonNull JSONObject payload) {
        queue.add(new Signal(type, payload));
        if (queue.size() >= ITEM_LIMIT) {
            triggerImmediateFlush();
        } else if (pendingFlush == null) {
            pendingFlush = scheduler.schedule(this::flush, SEND_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        }
    }

    public void pause() {
        paused.set(true);
    }

    public void unpause() {
        paused.set(false);
    }

    public void shutdown() {
        scheduler.shutdownNow();
    }

    private synchronized List<Signal> drain() {
        if (queue.isEmpty()) {
            return null;
        }
        List<Signal> snapshot = new ArrayList<>(queue);
        queue.clear();
        if (pendingFlush != null) {
            pendingFlush.cancel(false);
            pendingFlush = null;
        }
        return snapshot;
    }

    private void flush() {
        if (paused.get()) {
            return;
        }
        List<Signal> snapshot = drain();
        if (snapshot == null) {
            return;
        }
        JSONObject body = buildBody(snapshot);
        send(body);
    }

    private synchronized void triggerImmediateFlush() {
        if (pendingFlush != null) {
            pendingFlush.cancel(false);
            pendingFlush = null;
        }
        scheduler.execute(this::flush);
    }

    private JSONObject buildBody(@NonNull List<Signal> signals) {
        JSONObject body = new JSONObject();
        JsonUtils.putIfNotNull(body, "meta", meta.toJson());
        JSONArray logs = new JSONArray();
        JSONArray events = new JSONArray();
        JSONArray exceptions = new JSONArray();
        JSONArray measurements = new JSONArray();
        for (Signal signal : signals) {
            switch (signal.type) {
                case LOG:
                    logs.put(signal.payload);
                    break;
                case EVENT:
                    events.put(signal.payload);
                    break;
                case EXCEPTION:
                    exceptions.put(signal.payload);
                    break;
                case MEASUREMENT:
                    measurements.put(signal.payload);
                    break;
            }
        }
        if (logs.length() > 0) {
            JsonUtils.putIfNotNull(body, SignalType.LOG.getBodyKey(), logs);
        }
        if (events.length() > 0) {
            JsonUtils.putIfNotNull(body, SignalType.EVENT.getBodyKey(), events);
        }
        if (exceptions.length() > 0) {
            JsonUtils.putIfNotNull(body, SignalType.EXCEPTION.getBodyKey(), exceptions);
        }
        if (measurements.length() > 0) {
            JsonUtils.putIfNotNull(body, SignalType.MEASUREMENT.getBodyKey(), measurements);
        }
        return body;
    }

    private void send(@NonNull JSONObject body) {
        Request.Builder builder = new Request.Builder()
            .url(url)
            .post(RequestBody.create(body.toString(), JSON))
            .header("Content-Type", "application/json");
        if (apiKey != null && !apiKey.isEmpty()) {
            builder.header("x-api-key", apiKey);
        }
        FaroSession session = meta.getSession();
        if (session != null) {
            builder.header("x-faro-session-id", session.getId());
        }
        try (Response response = client.newCall(builder.build()).execute()) {
            if (!response.isSuccessful()) {
                Logger.warn(TAG, "Faro collector responded with " + response.code());
            }
        } catch (IOException exception) {
            Logger.warn(TAG, "Failed to send Faro signals: " + exception.getMessage());
        }
    }

    private static class Signal {

        @NonNull
        final JSONObject payload;

        @NonNull
        final SignalType type;

        Signal(@NonNull SignalType type, @NonNull JSONObject payload) {
            this.payload = payload;
            this.type = type;
        }
    }
}
