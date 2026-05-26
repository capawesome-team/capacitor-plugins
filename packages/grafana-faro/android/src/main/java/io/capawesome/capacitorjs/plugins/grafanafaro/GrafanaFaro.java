package io.capawesome.capacitorjs.plugins.grafanafaro;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.grafanafaro.anr.AnrWatchdog;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.FaroMeta;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.FaroSession;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.FaroTransport;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.JsonUtils;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.Timestamps;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.AppMetadata;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.InitializeOptions;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.InstrumentationsOptions;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.PushErrorOptions;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.PushEventOptions;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.PushLogOptions;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.PushMeasurementOptions;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.SetSessionOptions;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.StackFrameOptions;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.UserMetadata;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.ViewMetadata;
import io.capawesome.capacitorjs.plugins.grafanafaro.crash.CrashReporter;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class GrafanaFaro {

    @NonNull
    private final Context context;

    @Nullable
    private AnrWatchdog anrWatchdog;

    @Nullable
    private CrashReporter crashReporter;

    @Nullable
    private FaroMeta meta;

    @Nullable
    private FaroTransport transport;

    private double sessionSamplingRate = 1.0;

    public GrafanaFaro(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    public synchronized void initialize(@NonNull InitializeOptions options) throws Exception {
        InstrumentationsOptions instrumentations = options.getInstrumentations();
        initializeInternal(
            options.getUrl(),
            options.getApiKey(),
            options.getApp(),
            options.getSessionSamplingRate(),
            options.getSessionAttributes(),
            options.getUser(),
            options.getView(),
            options.isPaused(),
            instrumentations.isNativeCrashReportingEnabled(),
            instrumentations.isAnrTrackingEnabled()
        );
    }

    public synchronized void initializeFromConfig(@NonNull GrafanaFaroConfig config) throws Exception {
        if (config.getUrl() == null || config.getAppName() == null) {
            return;
        }
        JSObject appObject = new JSObject();
        appObject.put("name", config.getAppName());
        if (config.getAppVersion() != null) {
            appObject.put("version", config.getAppVersion());
        }
        if (config.getAppEnvironment() != null) {
            appObject.put("environment", config.getAppEnvironment());
        }
        if (config.getAppNamespace() != null) {
            appObject.put("namespace", config.getAppNamespace());
        }
        AppMetadata app = new AppMetadata(appObject);
        initializeInternal(
            config.getUrl(),
            config.getApiKey(),
            app,
            1.0,
            null,
            null,
            null,
            false,
            config.isNativeCrashReportingEnabled(),
            config.isAnrTrackingEnabled()
        );
    }

    private synchronized void initializeInternal(
        @NonNull String url,
        @Nullable String apiKey,
        @NonNull AppMetadata app,
        double sessionSamplingRate,
        @Nullable java.util.Map<String, String> sessionAttributes,
        @Nullable UserMetadata user,
        @Nullable ViewMetadata view,
        boolean paused,
        boolean nativeCrashReporting,
        boolean anrTracking
    ) throws Exception {
        if (transport != null) {
            throw new Exception(GrafanaFaroPlugin.ERROR_ALREADY_INITIALIZED);
        }
        this.sessionSamplingRate = sessionSamplingRate;
        FaroMeta meta = new FaroMeta(context, app);
        FaroSession session = FaroSession.create(sessionSamplingRate);
        session.setAttributes(sessionAttributes);
        meta.setSession(session);
        meta.setUser(user);
        meta.setView(view);
        FaroTransport transport = new FaroTransport(url, apiKey, meta, paused);
        this.meta = meta;
        this.transport = transport;

        if (nativeCrashReporting) {
            CrashReporter reporter = new CrashReporter(context);
            reporter.install();
            this.crashReporter = reporter;
            for (CrashReporter.PreviousCrash crash : reporter.collectPreviousCrashes()) {
                pushPreviousCrash(crash);
            }
        }
        if (anrTracking) {
            AnrWatchdog watchdog = new AnrWatchdog(this::reportAnr);
            watchdog.startMonitoring();
            this.anrWatchdog = watchdog;
        }
    }

    @NonNull
    public JSObject getSession() {
        JSObject result = new JSObject();
        if (meta == null) {
            return result;
        }
        FaroSession session = meta.getSession();
        if (session == null) {
            return result;
        }
        result.put("id", session.getId());
        if (!session.getAttributes().isEmpty()) {
            result.put("attributes", JsonUtils.mapToJson(session.getAttributes()));
        }
        return result;
    }

    @NonNull
    public JSObject getView() {
        JSObject result = new JSObject();
        if (meta == null) {
            return result;
        }
        ViewMetadata view = meta.getView();
        if (view != null) {
            result.put("name", view.getName());
        }
        return result;
    }

    public void pause() throws Exception {
        requireTransport().pause();
    }

    public void unpause() throws Exception {
        requireTransport().unpause();
    }

    public void pushLog(@NonNull PushLogOptions options) throws Exception {
        FaroTransport transport = requireTransport();
        JSONObject payload = new JSONObject();
        JsonUtils.putIfNotNull(payload, "message", options.getMessage());
        JsonUtils.putIfNotNull(payload, "level", options.getLevel());
        JsonUtils.putIfNotNull(payload, "timestamp", Timestamps.nowIso8601());
        if (options.getContext() != null && !options.getContext().isEmpty()) {
            JsonUtils.putIfNotNull(payload, "context", JsonUtils.mapToJson(options.getContext()));
        }
        transport.enqueue(FaroTransport.SignalType.LOG, payload);
    }

    public void pushEvent(@NonNull PushEventOptions options) throws Exception {
        FaroTransport transport = requireTransport();
        JSONObject payload = new JSONObject();
        JsonUtils.putIfNotNull(payload, "name", options.getName());
        JsonUtils.putIfNotNull(payload, "domain", options.getDomain());
        JsonUtils.putIfNotNull(payload, "timestamp", Timestamps.nowIso8601());
        if (options.getAttributes() != null && !options.getAttributes().isEmpty()) {
            JsonUtils.putIfNotNull(payload, "attributes", JsonUtils.mapToJson(options.getAttributes()));
        }
        transport.enqueue(FaroTransport.SignalType.EVENT, payload);
    }

    public void pushError(@NonNull PushErrorOptions options) throws Exception {
        FaroTransport transport = requireTransport();
        JSONObject payload = new JSONObject();
        JsonUtils.putIfNotNull(payload, "type", options.getType());
        JsonUtils.putIfNotNull(payload, "value", options.getValue());
        JsonUtils.putIfNotNull(payload, "fatal", options.isFatal());
        JsonUtils.putIfNotNull(payload, "timestamp", Timestamps.nowIso8601());
        if (options.getContext() != null && !options.getContext().isEmpty()) {
            JsonUtils.putIfNotNull(payload, "context", JsonUtils.mapToJson(options.getContext()));
        }
        JSONArray frames = buildStackFrames(options.getStackFrames());
        if (frames != null && frames.length() > 0) {
            JSONObject stacktrace = new JSONObject();
            JsonUtils.putIfNotNull(stacktrace, "frames", frames);
            JsonUtils.putIfNotNull(payload, "stacktrace", stacktrace);
        }
        transport.enqueue(FaroTransport.SignalType.EXCEPTION, payload);
    }

    public void pushMeasurement(@NonNull PushMeasurementOptions options) throws Exception {
        FaroTransport transport = requireTransport();
        JSONObject payload = new JSONObject();
        JsonUtils.putIfNotNull(payload, "type", options.getType());
        JsonUtils.putIfNotNull(payload, "values", JsonUtils.mapToJson(options.getValues()));
        JsonUtils.putIfNotNull(payload, "timestamp", Timestamps.nowIso8601());
        if (options.getContext() != null && !options.getContext().isEmpty()) {
            JsonUtils.putIfNotNull(payload, "context", JsonUtils.mapToJson(options.getContext()));
        }
        transport.enqueue(FaroTransport.SignalType.MEASUREMENT, payload);
    }

    public void resetSession() throws Exception {
        FaroMeta meta = requireMeta();
        FaroSession session = FaroSession.create(sessionSamplingRate);
        meta.setSession(session);
    }

    public void resetUser() throws Exception {
        requireMeta().setUser(null);
    }

    public void setSession(@NonNull SetSessionOptions options) throws Exception {
        FaroMeta meta = requireMeta();
        FaroSession session = FaroSession.createWithId(options.getId(), true);
        session.setAttributes(options.getAttributes());
        meta.setSession(session);
    }

    public void setUser(@NonNull UserMetadata user) throws Exception {
        requireMeta().setUser(user);
    }

    public void setView(@NonNull ViewMetadata view) throws Exception {
        requireMeta().setView(view);
    }

    public void shutdown() {
        if (anrWatchdog != null) {
            anrWatchdog.stopMonitoring();
            anrWatchdog = null;
        }
        if (transport != null) {
            transport.shutdown();
            transport = null;
        }
    }

    private void pushPreviousCrash(@NonNull CrashReporter.PreviousCrash crash) {
        FaroTransport transport = this.transport;
        if (transport == null) {
            return;
        }
        JSONObject payload = new JSONObject();
        JsonUtils.putIfNotNull(payload, "type", crash.type);
        JsonUtils.putIfNotNull(payload, "value", crash.message);
        JsonUtils.putIfNotNull(payload, "fatal", true);
        JsonUtils.putIfNotNull(payload, "timestamp", Timestamps.iso8601(crash.timestampMs));
        transport.enqueue(FaroTransport.SignalType.EXCEPTION, payload);
    }

    private void reportAnr(@NonNull Throwable throwable) {
        FaroTransport transport = this.transport;
        if (transport == null) {
            return;
        }
        JSONObject payload = new JSONObject();
        JsonUtils.putIfNotNull(payload, "type", "ANR");
        JsonUtils.putIfNotNull(payload, "value", throwable.getMessage());
        JsonUtils.putIfNotNull(payload, "fatal", false);
        JsonUtils.putIfNotNull(payload, "timestamp", Timestamps.nowIso8601());
        JSONArray frames = new JSONArray();
        for (StackTraceElement element : throwable.getStackTrace()) {
            JSONObject frame = new JSONObject();
            JsonUtils.putIfNotNull(frame, "filename", element.getFileName());
            JsonUtils.putIfNotNull(frame, "function", element.getClassName() + "." + element.getMethodName());
            int line = element.getLineNumber();
            if (line >= 0) {
                JsonUtils.putIfNotNull(frame, "lineno", line);
            }
            frames.put(frame);
        }
        if (frames.length() > 0) {
            JSONObject stacktrace = new JSONObject();
            JsonUtils.putIfNotNull(stacktrace, "frames", frames);
            JsonUtils.putIfNotNull(payload, "stacktrace", stacktrace);
        }
        transport.enqueue(FaroTransport.SignalType.EXCEPTION, payload);
    }

    @Nullable
    private JSONArray buildStackFrames(@Nullable List<StackFrameOptions> source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        JSONArray frames = new JSONArray();
        for (StackFrameOptions frame : source) {
            JSONObject json = new JSONObject();
            JsonUtils.putIfNotNull(json, "filename", frame.getFileName() != null ? frame.getFileName() : "");
            JsonUtils.putIfNotNull(json, "function", frame.getFunctionName() != null ? frame.getFunctionName() : "");
            JsonUtils.putIfNotNull(json, "lineno", frame.getLineNumber());
            JsonUtils.putIfNotNull(json, "colno", frame.getColumnNumber());
            frames.put(json);
        }
        return frames;
    }

    @NonNull
    private FaroMeta requireMeta() throws Exception {
        FaroMeta meta = this.meta;
        if (meta == null) {
            throw new Exception(GrafanaFaroPlugin.ERROR_NOT_INITIALIZED);
        }
        return meta;
    }

    @NonNull
    private FaroTransport requireTransport() throws Exception {
        FaroTransport transport = this.transport;
        if (transport == null) {
            throw new Exception(GrafanaFaroPlugin.ERROR_NOT_INITIALIZED);
        }
        return transport;
    }
}
