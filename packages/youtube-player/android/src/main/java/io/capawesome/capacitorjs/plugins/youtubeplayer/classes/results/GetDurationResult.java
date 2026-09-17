package io.capawesome.capacitorjs.plugins.youtubeplayer.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.youtubeplayer.interfaces.Result;

public class GetDurationResult implements Result {

    private final double duration;

    public GetDurationResult(double duration) {
        this.duration = duration;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("duration", duration);
        return result;
    }
}
