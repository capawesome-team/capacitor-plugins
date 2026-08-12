package io.capawesome.capacitorjs.plugins.grafanafaro.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JsonUtils {

    @Nullable
    public static Map<String, String> toStringMap(@Nullable JSObject source) {
        if (source == null) {
            return null;
        }
        Map<String, String> map = new LinkedHashMap<>();
        Iterator<String> keys = source.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = source.opt(key);
            if (value != null) {
                map.put(key, value.toString());
            }
        }
        return map;
    }

    @Nullable
    public static Map<String, Number> toNumberMap(@Nullable JSObject source) {
        if (source == null) {
            return null;
        }
        Map<String, Number> map = new LinkedHashMap<>();
        Iterator<String> keys = source.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = source.opt(key);
            if (value instanceof Number) {
                map.put(key, (Number) value);
            } else if (value != null) {
                try {
                    map.put(key, Double.parseDouble(value.toString()));
                } catch (NumberFormatException ignored) {
                    // Skip non-numeric values.
                }
            }
        }
        return map;
    }

    public static JSONObject mapToJson(@Nullable Map<String, ?> map) {
        JSONObject result = new JSONObject();
        if (map == null) {
            return result;
        }
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            try {
                result.put(entry.getKey(), entry.getValue());
            } catch (JSONException ignored) {
                // Skip invalid values.
            }
        }
        return result;
    }

    public static void putIfNotNull(@NonNull JSONObject target, @NonNull String key, @Nullable Object value) {
        if (value == null) {
            return;
        }
        try {
            target.put(key, value);
        } catch (JSONException ignored) {
            // Skip invalid values.
        }
    }

    @Nullable
    public static JSONArray toStringArray(@Nullable JSArray source) {
        if (source == null) {
            return null;
        }
        JSONArray result = new JSONArray();
        for (int i = 0; i < source.length(); i++) {
            Object value = source.opt(i);
            if (value != null) {
                result.put(value.toString());
            }
        }
        return result;
    }

    private JsonUtils() {}
}
