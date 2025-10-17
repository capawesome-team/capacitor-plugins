package io.capawesome.capacitorjs.plugins.posthog.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.posthog.PosthogHelper;
import java.util.Map;
import org.json.JSONException;

public class CaptureExceptionOptions {

    @NonNull
    private Object exception;

    @Nullable
    private Map<String, Object> properties;

    public CaptureExceptionOptions(@NonNull Object exception, @Nullable JSObject properties) throws JSONException {
        this.exception = exception;
        this.properties = PosthogHelper.createHashMapFromJSONObject(properties);
    }

    @NonNull
    public Object getException() {
        return exception;
    }

    @Nullable
    public Map<String, Object> getProperties() {
        return properties;
    }
}
