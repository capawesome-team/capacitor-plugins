package io.capawesome.capacitorjs.plugins.singular.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.singular.classes.CustomExceptions;

public class TrackEventOptions {

    @Nullable
    private final JSObject attributes;

    @NonNull
    private final String name;

    public TrackEventOptions(@NonNull PluginCall call) throws Exception {
        String name = call.getString("name");
        if (name == null) {
            throw CustomExceptions.NAME_MISSING;
        }
        this.attributes = call.getObject("attributes");
        this.name = name;
    }

    @Nullable
    public JSObject getAttributes() {
        return attributes;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
