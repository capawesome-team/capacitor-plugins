package io.capawesome.capacitorjs.plugins.grafanafaro.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.JsonUtils;
import java.util.Map;

public class SetSessionOptions {

    @Nullable
    private final Map<String, String> attributes;

    @Nullable
    private final String id;

    public SetSessionOptions(@NonNull PluginCall call) {
        this.attributes = JsonUtils.toStringMap(call.getObject("attributes"));
        this.id = call.getString("id");
    }

    @Nullable
    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Nullable
    public String getId() {
        return id;
    }
}
