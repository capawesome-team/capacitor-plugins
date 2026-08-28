package io.capawesome.capacitorjs.plugins.flic.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.flic.enums.ScanStatus;
import io.capawesome.capacitorjs.plugins.flic.interfaces.Result;

public class ScanStatusChangedEvent implements Result {

    @NonNull
    private final ScanStatus status;

    public ScanStatusChangedEvent(@NonNull ScanStatus status) {
        this.status = status;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("status", status.getValue());
        return result;
    }
}
