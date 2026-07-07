package io.capawesome.capacitorjs.plugins.mapslauncher.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.mapslauncher.interfaces.Result;
import java.util.List;

public class GetAvailableAppsResult implements Result {

    @NonNull
    private final List<String> apps;

    public GetAvailableAppsResult(@NonNull List<String> apps) {
        this.apps = apps;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSArray appsArray = new JSArray();
        for (String app : apps) {
            appsArray.put(app);
        }
        JSObject result = new JSObject();
        result.put("apps", appsArray);
        return result;
    }
}
