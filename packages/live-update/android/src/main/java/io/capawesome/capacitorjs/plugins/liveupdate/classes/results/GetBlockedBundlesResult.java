package io.capawesome.capacitorjs.plugins.liveupdate.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;

public class GetBlockedBundlesResult implements Result {

    @NonNull
    private String[] bundleIds;

    public GetBlockedBundlesResult(@NonNull String[] bundleIds) {
        this.bundleIds = bundleIds;
    }

    public JSObject toJSObject() {
        JSArray bundleIdsResult = new JSArray();
        for (String bundleId : bundleIds) {
            bundleIdsResult.put(bundleId);
        }

        JSObject result = new JSObject();
        result.put("bundleIds", bundleIdsResult);
        return result;
    }
}
