package io.capawesome.capacitorjs.plugins.posthog.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.posthog.PosthogHelper;
import java.util.Map;
import org.json.JSONException;

public class SetPersonPropertiesOptions {

    @Nullable
    private final Map<String, Object> properties;

    @Nullable
    private final Map<String, Object> setOnceProperties;

    public SetPersonPropertiesOptions(@NonNull PluginCall call) throws JSONException {
        this.properties = PosthogHelper.createHashMapFromJSONObject(call.getObject("properties"));
        this.setOnceProperties = PosthogHelper.createHashMapFromJSONObject(call.getObject("setOnceProperties"));
    }

    @Nullable
    public Map<String, Object> getProperties() {
        return properties;
    }

    @Nullable
    public Map<String, Object> getSetOnceProperties() {
        return setOnceProperties;
    }
}
