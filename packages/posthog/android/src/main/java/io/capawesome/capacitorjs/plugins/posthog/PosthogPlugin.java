package io.capawesome.capacitorjs.plugins.posthog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.posthog.classes.options.AliasOptions;
import io.capawesome.capacitorjs.plugins.posthog.classes.options.CaptureExceptionOptions;
import io.capawesome.capacitorjs.plugins.posthog.classes.options.CaptureOptions;
import io.capawesome.capacitorjs.plugins.posthog.classes.options.GetFeatureFlagOptions;
import io.capawesome.capacitorjs.plugins.posthog.classes.options.GetFeatureFlagPayloadOptions;
import io.capawesome.capacitorjs.plugins.posthog.classes.options.GroupOptions;
import io.capawesome.capacitorjs.plugins.posthog.classes.options.IdentifyOptions;
import io.capawesome.capacitorjs.plugins.posthog.classes.options.IsFeatureEnabledOptions;
import io.capawesome.capacitorjs.plugins.posthog.classes.options.RegisterOptions;
import io.capawesome.capacitorjs.plugins.posthog.classes.options.ScreenOptions;
import io.capawesome.capacitorjs.plugins.posthog.classes.options.SetupOptions;
import io.capawesome.capacitorjs.plugins.posthog.classes.options.StartSessionRecordingOptions;
import io.capawesome.capacitorjs.plugins.posthog.classes.options.UnregisterOptions;
import io.capawesome.capacitorjs.plugins.posthog.interfaces.Result;

@CapacitorPlugin(name = "Posthog")
public class PosthogPlugin extends Plugin {

    public static final String ERROR_ALIAS_MISSING = "alias must be provided.";
    public static final String ERROR_API_KEY_MISSING = "apiKey must be provided.";
    public static final String ERROR_DISTINCT_ID_MISSING = "distinctId must be provided.";
    public static final String ERROR_EVENT_MISSING = "event must be provided.";
    public static final String ERROR_KEY_MISSING = "key must be provided.";
    public static final String ERROR_SCREEN_TITLE_MISSING = "screenTitle must be provided.";
    public static final String ERROR_TYPE_MISSING = "type must be provided.";
    public static final String ERROR_VALUE_MISSING = "value must be provided.";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "Posthog";

    private Posthog implementation;

    @Override
    public void load() {
        try {
            implementation = new Posthog(getPosthogConfig(), this);
        } catch (Exception exception) {
            Logger.error(PosthogPlugin.TAG, exception.getMessage(), exception);
        }
    }

    @PluginMethod
    public void alias(PluginCall call) {
        try {
            String alias = call.getString("alias");
            if (alias == null) {
                call.reject(ERROR_ALIAS_MISSING);
                return;
            }

            AliasOptions options = new AliasOptions(alias);

            implementation.alias(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void capture(PluginCall call) {
        try {
            String event = call.getString("event");
            if (event == null) {
                call.reject(ERROR_EVENT_MISSING);
                return;
            }
            JSObject properties = call.getObject("properties");

            CaptureOptions options = new CaptureOptions(event, properties);

            implementation.capture(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void captureException(PluginCall call) {
        try {
            Object exception = call.getData().get("exception");
            if (exception == null) {
                call.reject(ERROR_VALUE_MISSING);
                return;
            }
            JSObject properties = call.getObject("properties");

            CaptureExceptionOptions options = new CaptureExceptionOptions(exception, properties);

            implementation.captureException(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void flush(PluginCall call) {
        try {
            implementation.flush();
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getFeatureFlag(PluginCall call) {
        try {
            GetFeatureFlagOptions options = new GetFeatureFlagOptions(call);
            Result result = implementation.getFeatureFlag(options);
            resolveCall(call, result.toJSObject());
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getFeatureFlagPayload(PluginCall call) {
        try {
            GetFeatureFlagPayloadOptions options = new GetFeatureFlagPayloadOptions(call);
            Result result = implementation.getFeatureFlagPayload(options);
            resolveCall(call, result.toJSObject());
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void group(PluginCall call) {
        try {
            String type = call.getString("type");
            if (type == null) {
                call.reject(ERROR_TYPE_MISSING);
                return;
            }
            String key = call.getString("key");
            if (key == null) {
                call.reject(ERROR_KEY_MISSING);
                return;
            }
            JSObject groupProperties = call.getObject("groupProperties");

            GroupOptions options = new GroupOptions(type, key, groupProperties);

            implementation.group(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void identify(PluginCall call) {
        try {
            String distinctId = call.getString("distinctId");
            if (distinctId == null) {
                call.reject(ERROR_DISTINCT_ID_MISSING);
                return;
            }
            JSObject userProperties = call.getObject("userProperties");

            IdentifyOptions options = new IdentifyOptions(distinctId, userProperties);

            implementation.identify(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isFeatureEnabled(PluginCall call) {
        try {
            IsFeatureEnabledOptions options = new IsFeatureEnabledOptions(call);
            Result result = implementation.isFeatureEnabled(options);
            resolveCall(call, result.toJSObject());
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void register(PluginCall call) {
        try {
            String key = call.getString("key");
            if (key == null) {
                call.reject(ERROR_KEY_MISSING);
                return;
            }
            Object value = call.getData().get("value");
            if (value == null) {
                call.reject(ERROR_VALUE_MISSING);
                return;
            }

            RegisterOptions options = new RegisterOptions(key, value);

            implementation.register(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void reloadFeatureFlags(PluginCall call) {
        try {
            implementation.reloadFeatureFlags();
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void reset(PluginCall call) {
        try {
            implementation.reset();
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void screen(PluginCall call) {
        try {
            String screenTitle = call.getString("screenTitle");
            if (screenTitle == null) {
                call.reject(ERROR_SCREEN_TITLE_MISSING);
                return;
            }
            JSObject properties = call.getObject("properties");

            ScreenOptions options = new ScreenOptions(screenTitle, properties);

            implementation.screen(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setup(PluginCall call) {
        try {
            String apiKey = call.getString("apiKey");
            if (apiKey == null) {
                call.reject(ERROR_API_KEY_MISSING);
                return;
            }
            String host = call.getString("host", "https://us.i.posthog.com");

            SetupOptions options = new SetupOptions(apiKey, host);

            implementation.setup(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void startSessionRecording(PluginCall call) {
        try {
            Boolean linkedFlag = call.getBoolean("linkedFlag");
            Double sampling = call.getDouble("sampling");

            StartSessionRecordingOptions options = new StartSessionRecordingOptions(linkedFlag, sampling);

            implementation.startSessionRecording(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void stopSessionRecording(PluginCall call) {
        try {
            implementation.stopSessionRecording();
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void unregister(PluginCall call) {
        try {
            String key = call.getString("key");
            if (key == null) {
                call.reject(ERROR_KEY_MISSING);
                return;
            }

            UnregisterOptions options = new UnregisterOptions(key);

            implementation.unregister(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private PosthogConfig getPosthogConfig() {
        PosthogConfig config = new PosthogConfig();

        String apiKey = getConfig().getString("apiKey", config.getApiKey());
        config.setApiKey(apiKey);
        String host = getConfig().getString("host", config.getHost());
        config.setHost(host);
        boolean enableSessionReplay = getConfig().getBoolean("enableSessionReplay", config.getEnableSessionReplay());
        config.setEnableSessionReplay(enableSessionReplay);
        double sessionReplaySampling = getConfig().getDouble("sessionReplaySampling", config.getSessionReplaySampling());
        config.setSessionReplaySampling(sessionReplaySampling);
        boolean sessionReplayLinkedFlag = getConfig().getBoolean("sessionReplayLinkedFlag", config.getSessionReplayLinkedFlag());
        config.setSessionReplayLinkedFlag(sessionReplayLinkedFlag);
        boolean enableErrorTracking = getConfig().getBoolean("enableErrorTracking", config.getEnableErrorTracking());
        config.setEnableErrorTracking(enableErrorTracking);

        return config;
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable JSObject result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result);
        }
    }

    private void rejectCall(PluginCall call, Exception exception) {
        String message = exception.getMessage();
        message = (message != null) ? message : ERROR_UNKNOWN_ERROR;
        Logger.error(TAG, message, exception);
        call.reject(message);
    }
}
