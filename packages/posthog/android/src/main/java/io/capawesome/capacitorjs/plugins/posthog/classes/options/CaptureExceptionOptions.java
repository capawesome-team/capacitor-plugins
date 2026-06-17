package io.capawesome.capacitorjs.plugins.posthog.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.posthog.PosthogHelper;
import java.util.Map;
import org.json.JSONException;

public class CaptureExceptionOptions {

    @NonNull
    private String message;

    @Nullable
    private String name;

    @Nullable
    private String stack;

    @Nullable
    private Map<String, Object> properties;

    public CaptureExceptionOptions(@NonNull String message, @Nullable String name, @Nullable String stack, @Nullable JSObject properties)
        throws JSONException {
        this.message = message;
        this.name = name;
        this.stack = stack;
        this.properties = PosthogHelper.createHashMapFromJSONObject(properties);
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getStack() {
        return stack;
    }

    @Nullable
    public Map<String, Object> getProperties() {
        return properties;
    }
}
