package io.capawesome.capacitorjs.plugins.appshortcut;

import androidx.annotation.NonNull;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class AppShortcutHelper {

    @NonNull
    public static HashMap<String, String> createHashMapFromJSONObject(@NonNull JSONObject object) throws JSONException {
        HashMap<String, String> map = new HashMap<>();
        Iterator<String> keys = object.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            map.put(key, (String) object.get(key));
        }
        return map;
    }
}
