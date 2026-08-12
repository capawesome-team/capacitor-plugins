package io.capawesome.capacitorjs.plugins.deviceinfo.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.deviceinfo.interfaces.Result;

public class GetUptimeResult implements Result {

    private final long uptime;

    public GetUptimeResult(long uptime) {
        this.uptime = uptime;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("uptime", uptime);
        return result;
    }
}
