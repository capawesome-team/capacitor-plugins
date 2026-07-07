package io.capawesome.capacitorjs.plugins.proximitysensor.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.proximitysensor.classes.Measurement;
import io.capawesome.capacitorjs.plugins.proximitysensor.classes.interfaces.Result;

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
