package io.capawesome.capacitorjs.plugins.silentmode.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.silentmode.interfaces.Result;

public class GetRingerModeResult implements Result {

    @NonNull
    private final String mode;

    public GetRingerModeResult(@NonNull String mode) {
        this.mode = mode;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("mode", mode);
        return result;
    }
}
