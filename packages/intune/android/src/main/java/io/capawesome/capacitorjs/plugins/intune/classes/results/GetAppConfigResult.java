package io.capawesome.capacitorjs.plugins.intune.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.intune.interfaces.Result;
import java.util.List;
import java.util.Map;

public class GetAppConfigResult implements Result {

    @NonNull
    private final Map<String, List<String>> config;

    public GetAppConfigResult(@NonNull Map<String, List<String>> config) {
        this.config = config;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject conflicts = new JSObject();
        JSObject values = new JSObject();
        JSArray conflictsArray = new JSArray();
        for (Map.Entry<String, List<String>> entry : config.entrySet()) {
            List<String> entryValues = entry.getValue();
            if (entryValues.isEmpty()) {
                continue;
            }
            values.put(entry.getKey(), entryValues.get(0));
            if (entryValues.size() > 1) {
                JSObject conflict = new JSObject();
                conflict.put("key", entry.getKey());
                JSArray conflictValues = new JSArray();
                for (String value : entryValues) {
                    conflictValues.put(value);
                }
                conflict.put("values", conflictValues);
                conflictsArray.put(conflict);
            }
        }
        JSObject result = new JSObject();
        result.put("conflicts", conflictsArray);
        result.put("values", values);
        return result;
    }
}
