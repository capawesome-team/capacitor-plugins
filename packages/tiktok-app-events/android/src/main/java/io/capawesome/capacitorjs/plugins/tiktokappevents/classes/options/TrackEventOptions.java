package io.capawesome.capacitorjs.plugins.tiktokappevents.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.tiktokappevents.classes.CustomExceptions;

public class TrackEventOptions {

    @Nullable
    private final String id;

    @NonNull
    private final String name;

    @Nullable
    private final JSObject properties;

    public TrackEventOptions(@NonNull PluginCall call) throws Exception {
        String name = call.getString("name");
        if (name == null) {
            throw CustomExceptions.NAME_MISSING;
        }
        this.name = name;
        this.id = call.getString("id");
        this.properties = call.getObject("properties");
    }

    @Nullable
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public JSObject getProperties() {
        return properties;
    }
}
