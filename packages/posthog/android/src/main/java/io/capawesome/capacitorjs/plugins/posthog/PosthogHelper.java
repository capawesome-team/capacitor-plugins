package io.capawesome.capacitorjs.plugins.posthog;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class PosthogHelper {
    @Nullable
    public static HashMap<String, Object> createHashMapFromJSONObject(@Nullable JSONObject object) throws JSONException {
        if (object == null) {
            return null;
        }
        HashMap<String, Object> map = new HashMap<>();
        Iterator<String> keys = object.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = createObjectFromJSValue(object.get(key));
            map.put(key, value);
        }
        return map;
    }

    public static Object createObjectFromJSValue(Object value) throws JSONException {
        if (value.toString().equals("null")) {
            return null;
        } else if (value instanceof JSONObject) {
            return createHashMapFromJSONObject((JSONObject) value);
        } else if (value instanceof JSONArray) {
            return createArrayListFromJSONArray((JSONArray) value);
        } else {
            return value;
        }
    }

    private static ArrayList<Object> createArrayListFromJSONArray(JSONArray array) throws JSONException {
        ArrayList<Object> arrayList = new ArrayList<>();
        for (int x = 0; x < array.length(); x++) {
            Object value = array.get(x);
            if (value instanceof JSONObject) {
                value = createHashMapFromJSONObject((JSONObject) value);
            } else if (value instanceof JSONArray) {
                value = createArrayListFromJSONArray((JSONArray) value);
            }
            arrayList.add(value);
        }
        return arrayList;
    }
}
