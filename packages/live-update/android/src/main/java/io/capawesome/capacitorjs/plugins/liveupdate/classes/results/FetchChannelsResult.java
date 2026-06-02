package io.capawesome.capacitorjs.plugins.liveupdate.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;

public class FetchChannelsResult implements Result {

    @NonNull
    private final ChannelResult[] channels;

    public FetchChannelsResult(@NonNull ChannelResult[] channels) {
        this.channels = channels;
    }

    @NonNull
    @Override
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        JSArray channelsArray = new JSArray();
        for (ChannelResult channel : channels) {
            channelsArray.put(channel.toJSObject());
        }
        result.put("channels", channelsArray);
        return result;
    }
}
