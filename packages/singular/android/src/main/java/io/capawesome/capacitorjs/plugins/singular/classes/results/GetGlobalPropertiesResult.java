package io.capawesome.capacitorjs.plugins.singular.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.singular.interfaces.Result;
import java.util.Map;

public class GetGlobalPropertiesResult implements Result {

    @Nullable
    private final Map<String, String> properties;

    public GetGlobalPropertiesResult(@Nullable Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject properties = new JSObject();
        if (this.properties != null) {
            for (Map.Entry<String, String> property : this.properties.entrySet()) {
                properties.put(property.getKey(), property.getValue());
            }
        }
        JSObject result = new JSObject();
        result.put("properties", properties);
        return result;
    }
}
