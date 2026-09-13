package io.capawesome.capacitorjs.plugins.singular.classes.events;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.singular.sdk.SingularLinkParams;
import io.capawesome.capacitorjs.plugins.singular.interfaces.Result;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class SingularLinkResolvedEvent implements Result {

    @NonNull
    private final SingularLinkParams linkParams;

    public SingularLinkResolvedEvent(@NonNull SingularLinkParams linkParams) {
        this.linkParams = linkParams;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        String deepLink = linkParams.getDeeplink();
        String passthrough = linkParams.getPassthrough();
        JSObject event = new JSObject();
        event.put("deepLink", deepLink == null ? JSONObject.NULL : deepLink);
        event.put("isDeferred", linkParams.isDeferred());
        event.put("passthrough", passthrough == null ? JSONObject.NULL : passthrough);
        event.put("urlParameters", createUrlParameters(linkParams.getUrlParameters()));
        return event;
    }

    @NonNull
    private JSObject createUrlParameters(@Nullable HashMap<String, String> parameters) {
        JSObject urlParameters = new JSObject();
        if (parameters != null) {
            for (Map.Entry<String, String> parameter : parameters.entrySet()) {
                urlParameters.put(parameter.getKey(), parameter.getValue());
            }
        }
        return urlParameters;
    }
}
