package io.capawesome.capacitorjs.plugins.compass.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.compass.classes.interfaces.Result;
import org.json.JSONObject;

public class Heading implements Result {

    @Nullable
    private final Double accuracy;

    private final double magneticHeading;

    @Nullable
    private final Double trueHeading;

    public Heading(double magneticHeading, @Nullable Double trueHeading, @Nullable Double accuracy) {
        this.magneticHeading = magneticHeading;
        this.trueHeading = trueHeading;
        this.accuracy = accuracy;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("magneticHeading", magneticHeading);
        result.put("trueHeading", trueHeading == null ? JSONObject.NULL : trueHeading);
        result.put("accuracy", accuracy == null ? JSONObject.NULL : accuracy);
        return result;
    }
}
