package io.capawesome.capacitorjs.plugins.grafanafaro.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.grafanafaro.GrafanaFaroPlugin;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.JsonUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InitializeOptions {

    @Nullable
    private final String apiKey;

    @NonNull
    private final AppMetadata app;

    @Nullable
    private final List<String> ignoreErrors;

    @Nullable
    private final List<String> ignoreUrls;

    @NonNull
    private final InstrumentationsOptions instrumentations;

    private final boolean paused;

    @Nullable
    private final Map<String, String> sessionAttributes;

    private final double sessionSamplingRate;

    @NonNull
    private final String url;

    @Nullable
    private final UserMetadata user;

    @Nullable
    private final ViewMetadata view;

    public InitializeOptions(@NonNull PluginCall call) throws Exception {
        String url = call.getString("url");
        if (url == null || url.isEmpty()) {
            throw new Exception(GrafanaFaroPlugin.ERROR_URL_MISSING);
        }
        JSObject app = call.getObject("app");
        if (app == null) {
            throw new Exception(GrafanaFaroPlugin.ERROR_APP_MISSING);
        }
        this.apiKey = call.getString("apiKey");
        this.app = new AppMetadata(app);
        this.ignoreErrors = parseStringList(call.getArray("ignoreErrors"));
        this.ignoreUrls = parseStringList(call.getArray("ignoreUrls"));
        JSObject instrumentations = call.getObject("instrumentations");
        this.instrumentations = new InstrumentationsOptions(instrumentations != null ? instrumentations : new JSObject());
        this.paused = call.getBoolean("paused", false);
        this.sessionAttributes = JsonUtils.toStringMap(call.getObject("sessionAttributes"));
        Double samplingRate = call.getDouble("sessionSamplingRate");
        this.sessionSamplingRate = samplingRate != null ? samplingRate : 1.0;
        this.url = url;
        JSObject user = call.getObject("user");
        this.user = user != null ? new UserMetadata(user) : null;
        JSObject view = call.getObject("view");
        this.view = view != null ? new ViewMetadata(view) : null;
    }

    @Nullable
    public String getApiKey() {
        return apiKey;
    }

    @NonNull
    public AppMetadata getApp() {
        return app;
    }

    @Nullable
    public List<String> getIgnoreErrors() {
        return ignoreErrors;
    }

    @Nullable
    public List<String> getIgnoreUrls() {
        return ignoreUrls;
    }

    @NonNull
    public InstrumentationsOptions getInstrumentations() {
        return instrumentations;
    }

    @Nullable
    public Map<String, String> getSessionAttributes() {
        return sessionAttributes;
    }

    public double getSessionSamplingRate() {
        return sessionSamplingRate;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    @Nullable
    public UserMetadata getUser() {
        return user;
    }

    @Nullable
    public ViewMetadata getView() {
        return view;
    }

    public boolean isPaused() {
        return paused;
    }

    @Nullable
    private static List<String> parseStringList(@Nullable JSArray array) {
        if (array == null) {
            return null;
        }
        List<String> result = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.opt(i);
            if (value != null) {
                result.add(value.toString());
            }
        }
        return result;
    }
}
