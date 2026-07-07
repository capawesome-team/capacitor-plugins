package io.capawesome.capacitorjs.plugins.battery.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.battery.interfaces.Result;

public class BatteryLevelChangeEvent implements Result {

    private final float level;

    public BatteryLevelChangeEvent(float level) {
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
