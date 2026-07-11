package io.capawesome.capacitorjs.plugins.youtubeplayer.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.youtubeplayer.interfaces.Result;

public class GetCurrentTimeResult implements Result {

    private final double currentTime;

    public GetCurrentTimeResult(double currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("currentTime", currentTime);
        return result;
    }
}
