package io.capawesome.capacitorjs.plugins.pixlive.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.pixlive.interfaces.Result;

public class GetNearbyGPSPointsResult implements Result {

    @NonNull
    private final JSArray points;

    public GetNearbyGPSPointsResult(@NonNull JSArray points) {
        this.points = points;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("points", points);
        return result;
    }
}
