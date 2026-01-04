package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.interfaces.Result;

public class IsPairingInProgressResult implements Result {

    private final boolean inProgress;

    public IsPairingInProgressResult(boolean inProgress) {
        this.inProgress = inProgress;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("inProgress", inProgress);
        return result;
    }
}
