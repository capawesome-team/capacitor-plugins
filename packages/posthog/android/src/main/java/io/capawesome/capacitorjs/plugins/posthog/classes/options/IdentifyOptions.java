package io.capawesome.capacitorjs.plugins.posthog.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.getcapacitor.JSObject;

import org.json.JSONException;

import java.util.Map;

import io.capawesome.capacitorjs.plugins.posthog.PosthogHelper;

public class IdentifyOptions {
    @NonNull
    private String distinctId;

    @Nullable
    private Map<String, Object> userProperties;

    public IdentifyOptions(@NonNull String distinctId, @Nullable JSObject userProperties) throws JSONException {
        this.distinctId = distinctId;
        this.userProperties = PosthogHelper.createHashMapFromJSONObject(userProperties);
    }

    @NonNull
    public String getDistinctId() {
        return distinctId;
    }

    @Nullable
    public Map<String, Object> getUserProperties() {
        return userProperties;
    }
}
