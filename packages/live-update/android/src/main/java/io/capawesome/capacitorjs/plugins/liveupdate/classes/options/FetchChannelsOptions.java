package io.capawesome.capacitorjs.plugins.liveupdate.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class FetchChannelsOptions {

    @Nullable
    private final Integer limit;

    @Nullable
    private final Integer offset;

    @Nullable
    private final String query;

    public FetchChannelsOptions(@NonNull PluginCall call) {
        this.limit = call.getInt("limit");
        this.offset = call.getInt("offset");
        this.query = call.getString("query");
    }

    @Nullable
    public Integer getLimit() {
        return limit;
    }

    @Nullable
    public Integer getOffset() {
        return offset;
    }

    @Nullable
    public String getQuery() {
        return query;
    }
}
