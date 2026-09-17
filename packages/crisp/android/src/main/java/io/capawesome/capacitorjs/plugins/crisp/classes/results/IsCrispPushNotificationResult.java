package io.capawesome.capacitorjs.plugins.crisp.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.crisp.interfaces.Result;

public class IsCrispPushNotificationResult implements Result {

    private final boolean crisp;

    public IsCrispPushNotificationResult(boolean crisp) {
        this.crisp = crisp;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("crisp", crisp);
        return result;
    }
}
