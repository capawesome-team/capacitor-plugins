package io.capawesome.capacitorjs.plugins.grafanafaro.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.grafanafaro.GrafanaFaroPlugin;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.Constants;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.JsonUtils;
import java.util.Map;

public class PushEventOptions {

    @Nullable
    private final Map<String, String> attributes;

    @NonNull
    private final String domain;

    @NonNull
    private final String name;

    public PushEventOptions(@NonNull PluginCall call) throws Exception {
        String name = call.getString("name");
        if (name == null || name.isEmpty()) {
            throw new Exception(GrafanaFaroPlugin.ERROR_NAME_MISSING);
        }
        this.attributes = JsonUtils.toStringMap(call.getObject("attributes"));
        this.domain = call.getString("domain", Constants.DOMAIN_ANDROID);
        this.name = name;
    }

    @Nullable
    public Map<String, String> getAttributes() {
        return attributes;
    }

    @NonNull
    public String getDomain() {
        return domain;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
