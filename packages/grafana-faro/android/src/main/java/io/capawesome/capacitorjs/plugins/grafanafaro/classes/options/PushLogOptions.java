package io.capawesome.capacitorjs.plugins.grafanafaro.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.grafanafaro.GrafanaFaroPlugin;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.Constants;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.JsonUtils;
import java.util.Map;

public class PushLogOptions {

    @Nullable
    private final Map<String, String> context;

    @NonNull
    private final String level;

    @NonNull
    private final String message;

    public PushLogOptions(@NonNull PluginCall call) throws Exception {
        String message = call.getString("message");
        if (message == null) {
            throw new Exception(GrafanaFaroPlugin.ERROR_MESSAGE_MISSING);
        }
        this.context = JsonUtils.toStringMap(call.getObject("context"));
        this.level = call.getString("level", Constants.LEVEL_INFO);
        this.message = message;
    }

    @Nullable
    public Map<String, String> getContext() {
        return context;
    }

    @NonNull
    public String getLevel() {
        return level;
    }

    @NonNull
    public String getMessage() {
        return message;
    }
}
