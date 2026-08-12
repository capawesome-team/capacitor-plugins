package io.capawesome.capacitorjs.plugins.volume.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.volume.interfaces.Result;

public class GetVolumeResult implements Result {

    private final double volume;

    public GetVolumeResult(double volume) {
        this.volume = volume;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("volume", volume);
        return result;
    }
}
