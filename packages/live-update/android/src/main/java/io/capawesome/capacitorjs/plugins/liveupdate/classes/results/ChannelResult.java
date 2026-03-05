package io.capawesome.capacitorjs.plugins.liveupdate.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;

public class ChannelResult implements Result {

    @NonNull
    private final String id;

    @NonNull
    private final String name;

    public ChannelResult(@NonNull String id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    @NonNull
    @Override
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("id", id);
        result.put("name", name);
        return result;
    }
}
