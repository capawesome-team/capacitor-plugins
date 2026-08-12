package io.capawesome.capacitorjs.plugins.grafanafaro.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.grafanafaro.GrafanaFaroPlugin;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.JsonUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class PushErrorOptions {

    @Nullable
    private final Map<String, String> context;

    private final boolean fatal;

    @Nullable
    private final List<StackFrameOptions> stackFrames;

    @NonNull
    private final String type;

    @NonNull
    private final String value;

    public PushErrorOptions(@NonNull PluginCall call) throws Exception {
        String type = call.getString("type");
        if (type == null) {
            throw new Exception(GrafanaFaroPlugin.ERROR_TYPE_MISSING);
        }
        String value = call.getString("value");
        if (value == null) {
            throw new Exception(GrafanaFaroPlugin.ERROR_VALUE_MISSING);
        }
        this.context = JsonUtils.toStringMap(call.getObject("context"));
        this.fatal = call.getBoolean("fatal", false);
        this.stackFrames = parseStackFrames(call.getArray("stackFrames"));
        this.type = type;
        this.value = value;
    }

    @Nullable
    public Map<String, String> getContext() {
        return context;
    }

    @Nullable
    public List<StackFrameOptions> getStackFrames() {
        return stackFrames;
    }

    @NonNull
    public String getType() {
        return type;
    }

    @NonNull
    public String getValue() {
        return value;
    }

    public boolean isFatal() {
        return fatal;
    }

    @Nullable
    private static List<StackFrameOptions> parseStackFrames(@Nullable JSArray array) {
        if (array == null || array.length() == 0) {
            return null;
        }
        List<StackFrameOptions> result = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Object item = array.opt(i);
            if (item instanceof JSONObject) {
                try {
                    result.add(new StackFrameOptions(JSObject.fromJSONObject((JSONObject) item)));
                } catch (JSONException ignored) {
                    // Skip invalid frame entries.
                }
            }
        }
        return result.isEmpty() ? null : result;
    }
}
