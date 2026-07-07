package io.capawesome.capacitorjs.plugins.gyroscope.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.gyroscope.classes.Measurement;
import io.capawesome.capacitorjs.plugins.gyroscope.classes.interfaces.Result;

public class GetMeasurementResult implements Result {

    @NonNull
    private final Measurement measurement;

    public GetMeasurementResult(@NonNull Measurement measurement) {
        this.measurement = measurement;
    }

    @Override
    public JSObject toJSObject() {
        return measurement.toJSObject();
    }
}
