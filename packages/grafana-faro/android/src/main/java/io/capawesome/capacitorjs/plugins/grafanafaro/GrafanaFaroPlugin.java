package io.capawesome.capacitorjs.plugins.grafanafaro;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.InitializeOptions;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.PushErrorOptions;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.PushEventOptions;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.PushLogOptions;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.PushMeasurementOptions;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.SetSessionOptions;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.UserMetadata;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.ViewMetadata;
import org.json.JSONObject;

@CapacitorPlugin(name = "GrafanaFaro")
public class GrafanaFaroPlugin extends Plugin {

    public static final String TAG = "GrafanaFaro";

    public static final String ERROR_ALREADY_INITIALIZED = "GrafanaFaro: already initialized.";
    public static final String ERROR_APP_MISSING = "app must be provided.";
    public static final String ERROR_APP_NAME_MISSING = "app.name must be provided.";
    public static final String ERROR_MESSAGE_MISSING = "message must be provided.";
    public static final String ERROR_NAME_MISSING = "name must be provided.";
    public static final String ERROR_NOT_INITIALIZED = "GrafanaFaro: not initialized. Call `initialize(...)` first.";
    public static final String ERROR_TYPE_MISSING = "type must be provided.";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String ERROR_URL_MISSING = "url must be provided.";
    public static final String ERROR_VALUE_MISSING = "value must be provided.";
    public static final String ERROR_VALUES_MISSING = "values must be provided.";
    public static final String ERROR_VALUES_NOT_NUMERIC = "values must be a non-empty map of numbers.";

    private GrafanaFaro implementation;

    @Override
    public void load() {
        implementation = new GrafanaFaro(getContext());
        try {
            implementation.initializeFromConfig(getGrafanaFaroConfig());
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
        }
    }

    private GrafanaFaroConfig getGrafanaFaroConfig() {
        GrafanaFaroConfig config = new GrafanaFaroConfig();
        config.setApiKey(getConfig().getString("apiKey", config.getApiKey()));
        config.setAppEnvironment(getConfig().getString("appEnvironment", config.getAppEnvironment()));
        config.setAppName(getConfig().getString("appName", config.getAppName()));
        config.setAppNamespace(getConfig().getString("appNamespace", config.getAppNamespace()));
        config.setAppVersion(getConfig().getString("appVersion", config.getAppVersion()));
        config.setUrl(getConfig().getString("url", config.getUrl()));
        JSONObject instrumentations = getConfig().getObject("instrumentations");
        if (instrumentations != null) {
            config.setAnrTracking(instrumentations.optBoolean("anrTracking", config.isAnrTrackingEnabled()));
            config.setNativeCrashReporting(instrumentations.optBoolean("nativeCrashReporting", config.isNativeCrashReportingEnabled()));
        }
        return config;
    }

    @Override
    protected void handleOnDestroy() {
        if (implementation != null) {
            implementation.shutdown();
        }
        super.handleOnDestroy();
    }

    @PluginMethod
    public void getSession(PluginCall call) {
        try {
            JSObject result = implementation.getSession();
            call.resolve(result);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getView(PluginCall call) {
        try {
            JSObject result = implementation.getView();
            call.resolve(result);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void initialize(PluginCall call) {
        try {
            InitializeOptions options = new InitializeOptions(call);
            implementation.initialize(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void pause(PluginCall call) {
        try {
            implementation.pause();
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void pushError(PluginCall call) {
        try {
            PushErrorOptions options = new PushErrorOptions(call);
            implementation.pushError(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void pushEvent(PluginCall call) {
        try {
            PushEventOptions options = new PushEventOptions(call);
            implementation.pushEvent(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void pushLog(PluginCall call) {
        try {
            PushLogOptions options = new PushLogOptions(call);
            implementation.pushLog(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void pushMeasurement(PluginCall call) {
        try {
            PushMeasurementOptions options = new PushMeasurementOptions(call);
            implementation.pushMeasurement(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void resetSession(PluginCall call) {
        try {
            implementation.resetSession();
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void resetUser(PluginCall call) {
        try {
            implementation.resetUser();
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setSession(PluginCall call) {
        try {
            SetSessionOptions options = new SetSessionOptions(call);
            implementation.setSession(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setUser(PluginCall call) {
        try {
            UserMetadata user = new UserMetadata(call);
            implementation.setUser(user);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setView(PluginCall call) {
        try {
            ViewMetadata view = new ViewMetadata(call);
            implementation.setView(view);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void unpause(PluginCall call) {
        try {
            implementation.unpause();
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        message = (message != null) ? message : ERROR_UNKNOWN_ERROR;
        Logger.error(TAG, message, exception);
        call.reject(message);
    }
}
