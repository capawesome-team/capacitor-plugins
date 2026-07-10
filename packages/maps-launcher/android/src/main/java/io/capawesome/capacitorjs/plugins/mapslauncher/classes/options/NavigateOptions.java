package io.capawesome.capacitorjs.plugins.mapslauncher.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.mapslauncher.classes.CustomExceptions;

public class NavigateOptions {

    @Nullable
    private final String app;

    @NonNull
    private final Destination destination;

    @Nullable
    private final Destination start;

    @Nullable
    private final String travelMode;

    public NavigateOptions(@NonNull PluginCall call) throws Exception {
        JSObject destinationObject = call.getObject("destination");
        if (destinationObject == null) {
            throw CustomExceptions.DESTINATION_MISSING;
        }
        this.destination = new Destination(destinationObject);
        JSObject startObject = call.getObject("start");
        this.start = startObject == null ? null : new Destination(startObject);
        this.app = call.getString("app");
        this.travelMode = call.getString("travelMode");
    }

    @Nullable
    public String getApp() {
        return app;
    }

    @NonNull
    public Destination getDestination() {
        return destination;
    }

    @Nullable
    public Destination getStart() {
        return start;
    }

    @Nullable
    public String getTravelMode() {
        return travelMode;
    }
}
