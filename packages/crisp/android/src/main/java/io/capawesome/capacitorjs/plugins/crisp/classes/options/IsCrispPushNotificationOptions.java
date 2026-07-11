package io.capawesome.capacitorjs.plugins.crisp.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.crisp.classes.CustomExceptions;

public class IsCrispPushNotificationOptions {

    @NonNull
    private final JSObject data;

    public IsCrispPushNotificationOptions(@NonNull PluginCall call) throws Exception {
        JSObject data = call.getObject("data");
        if (data == null) {
            throw CustomExceptions.DATA_MISSING;
        }
        this.data = data;
    }

    @NonNull
    public JSObject getData() {
        return data;
    }
}
