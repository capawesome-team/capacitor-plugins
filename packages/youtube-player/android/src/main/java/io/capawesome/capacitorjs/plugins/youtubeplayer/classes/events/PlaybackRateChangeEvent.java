package io.capawesome.capacitorjs.plugins.youtubeplayer.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.youtubeplayer.interfaces.Result;

public class PlaybackRateChangeEvent implements Result {

    @NonNull
    private final String id;

    private final double rate;

    public PlaybackRateChangeEvent(@NonNull String id, double rate) {
        this.id = id;
        this.rate = rate;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("id", id);
        result.put("rate", rate);
        return result;
    }
}
