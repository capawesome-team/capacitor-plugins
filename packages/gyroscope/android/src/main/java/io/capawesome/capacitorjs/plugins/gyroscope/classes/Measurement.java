package io.capawesome.capacitorjs.plugins.gyroscope.classes;

import android.hardware.SensorEvent;
import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.gyroscope.classes.interfaces.Result;

public class Measurement implements Result {

    final double x;
    final double y;
    final double z;

    public Measurement(@NonNull SensorEvent sensorEvent) {
        this.x = Math.round(sensorEvent.values[0] * 100.0) / 100.0;
        this.y = Math.round(sensorEvent.values[1] * 100.0) / 100.0;
        this.z = Math.round(sensorEvent.values[2] * 100.0) / 100.0;
    }

    @Override
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("x", x);
        result.put("y", y);
        result.put("z", z);
        return result;
    }
}
