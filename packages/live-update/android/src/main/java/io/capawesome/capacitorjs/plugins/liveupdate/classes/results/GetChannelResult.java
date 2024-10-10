package io.capawesome.capacitorjs.plugins.liveupdate.classes.results;

import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;

public class GetChannelResult implements Result {

    @Nullable
    private String channel;

    public GetChannelResult(@Nullable String channel) {
        this.channel = channel;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("channel", channel == null ? JSONObject.NULL : channel);
        return result;
    }
}
