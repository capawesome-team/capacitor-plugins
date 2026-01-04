package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.interfaces.Result;

public class IsAuthorizedResult implements Result {

    private final boolean authorized;

    public IsAuthorizedResult(boolean authorized) {
        this.authorized = authorized;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("authorized", authorized);
        return result;
    }
}
