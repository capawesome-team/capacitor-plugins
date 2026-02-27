package io.capawesome.capacitorjs.plugins.liveupdate.classes.results;

import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;
import org.json.JSONObject;

public class GetDefaultChannelResult implements Result {

    @Nullable
    private String channel;

    public GetDefaultChannelResult(@Nullable String channel) {
        this.channel = channel;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("channel", channel == null ? JSONObject.NULL : channel);
        return result;
    }
}
