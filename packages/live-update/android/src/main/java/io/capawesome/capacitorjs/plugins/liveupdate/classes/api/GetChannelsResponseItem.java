package io.capawesome.capacitorjs.plugins.liveupdate.classes.api;

import androidx.annotation.NonNull;
import org.json.JSONObject;

public class GetChannelsResponseItem {

    @NonNull
    private final String id;

    @NonNull
    private final String name;

    public GetChannelsResponseItem(@NonNull JSONObject responseJson) {
        this.id = responseJson.optString("id");
        this.name = responseJson.optString("name");
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
