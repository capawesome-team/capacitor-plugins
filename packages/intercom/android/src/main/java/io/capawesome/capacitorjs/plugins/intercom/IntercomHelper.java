package io.capawesome.capacitorjs.plugins.intercom;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class IntercomHelper {

    @NonNull
    public static Map<String, Object> convertJSObjectToObjectMap(@NonNull JSObject data) {
        Map<String, Object> result = new HashMap<>();
        Iterator<String> keys = data.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = data.opt(key);
            if (value != null) {
                result.put(key, value);
            }
        }
        return result;
    }

    @NonNull
    public static Map<String, String> convertJSObjectToStringMap(@NonNull JSObject data) {
        Map<String, String> result = new HashMap<>();
        Iterator<String> keys = data.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = data.opt(key);
            if (value != null) {
                result.put(key, value.toString());
            }
        }
        return result;
    }
}
