package io.capawesome.capacitorjs.plugins.grafanafaro.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.grafanafaro.GrafanaFaroPlugin;

public class ViewMetadata {

    @NonNull
    private final String name;

    public ViewMetadata(@NonNull PluginCall call) throws Exception {
        String name = call.getString("name");
        if (name == null || name.isEmpty()) {
            throw new Exception(GrafanaFaroPlugin.ERROR_NAME_MISSING);
        }
        this.name = name;
    }

    public ViewMetadata(@NonNull JSObject source) throws Exception {
        String name = source.getString("name");
        if (name == null || name.isEmpty()) {
            throw new Exception(GrafanaFaroPlugin.ERROR_NAME_MISSING);
        }
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
