package io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.Location;
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.interfaces.Result;

public class RouteDeviationDetectedEvent implements Result {

    private final double deviationMeters;

    @NonNull
    private final Location location;

    public RouteDeviationDetectedEvent(double deviationMeters, @NonNull Location location) {
        this.deviationMeters = deviationMeters;
        this.location = location;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("deviationMeters", deviationMeters);
        result.put("location", location.toJSObject());
        return result;
    }
}
