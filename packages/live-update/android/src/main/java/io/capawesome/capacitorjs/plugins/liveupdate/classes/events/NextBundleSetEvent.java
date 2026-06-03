package io.capawesome.capacitorjs.plugins.liveupdate.classes.events;

import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import org.json.JSONObject;

public class NextBundleSetEvent {

    @Nullable
    private final String bundleId;

    public NextBundleSetEvent(@Nullable String bundleId) {
        this.bundleId = bundleId;
    }

    @Nullable
    public String getBundleId() {
        return bundleId;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("bundleId", bundleId == null ? JSONObject.NULL : bundleId);
        return result;
    }
}
