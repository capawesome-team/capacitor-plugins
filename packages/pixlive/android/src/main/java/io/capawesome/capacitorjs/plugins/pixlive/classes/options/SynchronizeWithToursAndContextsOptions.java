package io.capawesome.capacitorjs.plugins.pixlive.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.pixlive.classes.CustomExceptions;
import org.json.JSONArray;

public class SynchronizeWithToursAndContextsOptions {

    @NonNull
    private final JSONArray tags;

    @NonNull
    private final JSONArray tourIds;

    @NonNull
    private final JSONArray contextIds;

    public SynchronizeWithToursAndContextsOptions(@NonNull PluginCall call) throws Exception {
        this.tags = SynchronizeWithToursAndContextsOptions.getTagsFromCall(call);
        this.tourIds = SynchronizeWithToursAndContextsOptions.getTourIdsFromCall(call);
        this.contextIds = SynchronizeWithToursAndContextsOptions.getContextIdsFromCall(call);
    }

    @NonNull
    public JSONArray getTags() {
        return tags;
    }

    @NonNull
    public JSONArray getTourIds() {
        return tourIds;
    }

    @NonNull
    public JSONArray getContextIds() {
        return contextIds;
    }

    @NonNull
    private static JSONArray getTagsFromCall(@NonNull PluginCall call) throws Exception {
        JSONArray tags = call.getArray("tags");
        if (tags == null) {
            throw CustomExceptions.TAGS_MISSING;
        }
        return tags;
    }

    @NonNull
    private static JSONArray getTourIdsFromCall(@NonNull PluginCall call) throws Exception {
        JSONArray tourIds = call.getArray("tourIds");
        if (tourIds == null) {
            throw CustomExceptions.TOUR_IDS_MISSING;
        }
        return tourIds;
    }

    @NonNull
    private static JSONArray getContextIdsFromCall(@NonNull PluginCall call) throws Exception {
        JSONArray contextIds = call.getArray("contextIds");
        if (contextIds == null) {
            throw CustomExceptions.CONTEXT_IDS_MISSING;
        }
        return contextIds;
    }
}
