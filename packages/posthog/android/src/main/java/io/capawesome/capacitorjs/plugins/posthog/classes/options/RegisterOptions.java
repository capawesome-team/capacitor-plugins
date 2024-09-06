package io.capawesome.capacitorjs.plugins.posthog.classes.options;

import androidx.annotation.NonNull;

import org.json.JSONException;

import io.capawesome.capacitorjs.plugins.posthog.PosthogHelper;

public class RegisterOptions {
    @NonNull
    private String key;

    @NonNull
    private Object value;

    public RegisterOptions(@NonNull String key, @NonNull Object value) throws JSONException {
        this.key = key;
        this.value = PosthogHelper.createObjectFromJSValue(value);
    }

    @NonNull
    public String getKey() {
        return key;
    }

    @NonNull
    public Object getValue() {
        return value;
    }
}
