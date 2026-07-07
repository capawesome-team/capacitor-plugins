package io.capawesome.capacitorjs.plugins.lightsensor.classes;

import android.hardware.SensorEvent;
import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.lightsensor.classes.interfaces.Result;

public class Measurement implements Result {

    final double illuminance;

    public Measurement(@NonNull SensorEvent sensorEvent) {
        this.illuminance = Math.round(sensorEvent.values[0] * 100.0) / 100.0;
    }

    @Override
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("illuminance", illuminance);
        return result;
    }
}
