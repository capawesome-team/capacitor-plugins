package io.capawesome.capacitorjs.plugins.singular.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.singular.interfaces.Result;

public class CreateReferrerShortLinkResult implements Result {

    @NonNull
    private final String link;

    public CreateReferrerShortLinkResult(@NonNull String link) {
        this.link = link;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("link", link);
        return result;
    }
}
