package io.capawesome.capacitorjs.plugins.intercom.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.intercom.classes.CustomExceptions;

public class LogEventOptions {

    @Nullable
    private final JSObject data;

    @NonNull
    private final String name;

    public LogEventOptions(@NonNull PluginCall call) throws Exception {
        String name = call.getString("name");
        if (name == null) {
            throw CustomExceptions.NAME_MISSING;
        }
        this.name = name;
        this.data = call.getObject("data");
    }

    @Nullable
    public JSObject getData() {
        return data;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
