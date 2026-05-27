package io.capawesome.capacitorjs.plugins.grafanafaro.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.grafanafaro.GrafanaFaroPlugin;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.JsonUtils;
import java.util.Map;

public class PushMeasurementOptions {

    @Nullable
    private final Map<String, String> context;

    @NonNull
    private final String type;

    @NonNull
    private final Map<String, Number> values;

    public PushMeasurementOptions(@NonNull PluginCall call) throws Exception {
        String type = call.getString("type");
        if (type == null || type.isEmpty()) {
            throw new Exception(GrafanaFaroPlugin.ERROR_TYPE_MISSING);
        }
        JSObject values = call.getObject("values");
        if (values == null) {
            throw new Exception(GrafanaFaroPlugin.ERROR_VALUES_MISSING);
        }
        Map<String, Number> numericValues = JsonUtils.toNumberMap(values);
        if (numericValues == null || numericValues.isEmpty()) {
            throw new Exception(GrafanaFaroPlugin.ERROR_VALUES_NOT_NUMERIC);
        }
        this.context = JsonUtils.toStringMap(call.getObject("context"));
        this.type = type;
        this.values = numericValues;
    }

    @Nullable
    public Map<String, String> getContext() {
        return context;
    }

    @NonNull
    public String getType() {
        return type;
    }

    @NonNull
    public Map<String, Number> getValues() {
        return values;
    }
}
