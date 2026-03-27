package io.capawesome.capacitorjs.plugins.pixlive.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.pixlive.classes.CustomExceptions;
import org.json.JSONArray;

public class SynchronizeOptions {

    @NonNull
    private final JSONArray tags;

    public SynchronizeOptions(@NonNull PluginCall call) throws Exception {
        this.tags = SynchronizeOptions.getTagsFromCall(call);
    }

    @NonNull
    public JSONArray getTags() {
        return tags;
    }

    @NonNull
    private static JSONArray getTagsFromCall(@NonNull PluginCall call) throws Exception {
        JSONArray tags = call.getArray("tags");
        if (tags == null) {
            throw CustomExceptions.TAGS_MISSING;
        }
        return tags;
    }
}
