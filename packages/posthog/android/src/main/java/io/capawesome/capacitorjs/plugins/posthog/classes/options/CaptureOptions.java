package io.capawesome.capacitorjs.plugins.posthog.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.getcapacitor.JSObject;

import org.json.JSONException;

import java.util.Map;

import io.capawesome.capacitorjs.plugins.posthog.PosthogHelper;

public class CaptureOptions {
    @NonNull
    private String event;

    @Nullable
    private Map<String, Object> properties;

    public CaptureOptions(@NonNull String event, @Nullable JSObject properties) throws JSONException {
        this.event = event;
        this.properties = PosthogHelper.createHashMapFromJSONObject(properties);
    }

    @NonNull
    public String getEvent() {
        return event;
    }

    @Nullable
    public Map<String, Object> getProperties() {
        return properties;
    }
}
