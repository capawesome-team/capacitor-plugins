package io.capawesome.capacitorjs.plugins.battery.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.battery.interfaces.Result;

public class GetBatteryLevelResult implements Result {

    private final float level;

    public GetBatteryLevelResult(float level) {
        this.level = level;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("level", level);
        return result;
    }
}
