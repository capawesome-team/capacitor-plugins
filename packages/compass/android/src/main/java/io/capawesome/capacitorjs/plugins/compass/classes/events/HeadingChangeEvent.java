package io.capawesome.capacitorjs.plugins.compass.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.compass.classes.Heading;
import io.capawesome.capacitorjs.plugins.compass.classes.interfaces.Result;

public class HeadingChangeEvent implements Result {

    @NonNull
    private final Heading heading;

    public HeadingChangeEvent(@NonNull Heading heading) {
        this.heading = heading;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        return heading.toJSObject();
    }
}
