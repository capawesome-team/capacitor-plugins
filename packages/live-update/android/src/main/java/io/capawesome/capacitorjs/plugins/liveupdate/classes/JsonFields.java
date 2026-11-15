package io.capawesome.capacitorjs.plugins.liveupdate.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.json.JSONObject;

public class JsonFields {

    /**
     * Unlike {@link JSONObject#optString(String)}, this never returns the text "null" for a JSON null
     * and never an empty text for a missing key.
     */
    @Nullable
    public static String getNonEmptyString(@NonNull JSONObject json, @NonNull String key) {
        Object value = json.opt(key);
        if (!(value instanceof String) || ((String) value).isEmpty()) {
            return null;
        }
        return (String) value;
    }
}
