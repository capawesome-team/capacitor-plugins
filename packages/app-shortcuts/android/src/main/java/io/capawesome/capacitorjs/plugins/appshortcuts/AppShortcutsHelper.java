package io.capawesome.capacitorjs.plugins.appshortcuts;

import androidx.annotation.NonNull;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class AppShortcutsHelper {

    @NonNull
    public static HashMap<String, Object> createHashMapFromJSONObject(@NonNull JSONObject object) throws JSONException {
        HashMap<String, Object> map = new HashMap<>();
        Iterator<String> keys = object.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            map.put(key, object.get(key));
        }
        return map;
    }
}
