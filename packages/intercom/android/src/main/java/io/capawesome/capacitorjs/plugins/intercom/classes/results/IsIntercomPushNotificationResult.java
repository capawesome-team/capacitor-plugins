package io.capawesome.capacitorjs.plugins.intercom.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.intercom.interfaces.Result;

public class IsIntercomPushNotificationResult implements Result {

    private final boolean intercom;

    public IsIntercomPushNotificationResult(boolean intercom) {
        this.intercom = intercom;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("intercom", intercom);
        return result;
    }
}
