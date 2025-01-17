package io.capawesome.capacitorjs.plugins.posthog.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.posthog.PosthogHelper;
import java.util.Map;
import org.json.JSONException;

public class GroupOptions {

    @NonNull
    private String type;

    @NonNull
    private String key;

    @Nullable
    private Map<String, Object> groupProperties;

    public GroupOptions(@NonNull String type, @NonNull String key, @Nullable JSObject groupProperties) throws JSONException {
        this.type = type;
        this.key = key;
        this.groupProperties = PosthogHelper.createHashMapFromJSONObject(groupProperties);
    }

    @NonNull
    public String getType() {
        return type;
    }

    @NonNull
    public String getKey() {
        return key;
    }

    @Nullable
    public Map<String, Object> getGroupProperties() {
        return groupProperties;
    }
}
