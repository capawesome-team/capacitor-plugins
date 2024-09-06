package io.capawesome.capacitorjs.plugins.posthog.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.posthog.PosthogHelper;
import java.util.Map;
import org.json.JSONException;

public class ScreenOptions {

    @NonNull
    private String screenTitle;

    @Nullable
    private Map<String, Object> properties;

    public ScreenOptions(@NonNull String screenTitle, @Nullable JSObject properties) throws JSONException {
        this.screenTitle = screenTitle;
        this.properties = PosthogHelper.createHashMapFromJSONObject(properties);
    }

    @NonNull
    public String getScreenTitle() {
        return screenTitle;
    }

    @Nullable
    public Map<String, Object> getProperties() {
        return properties;
    }
}
