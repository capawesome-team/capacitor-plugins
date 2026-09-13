package io.capawesome.capacitorjs.plugins.singular.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.singular.interfaces.Result;

public class SdidReceivedEvent implements Result {

    @NonNull
    private final String sdid;

    public SdidReceivedEvent(@NonNull String sdid) {
        this.sdid = sdid;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject event = new JSObject();
        event.put("sdid", sdid);
        return event;
    }
}
