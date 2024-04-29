package io.capawesome.capacitorjs.plugins.liveupdate.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;
import org.json.JSONObject;

public class GetDeviceIdResult implements Result {

    @NonNull
    private String deviceId;

    public GetDeviceIdResult(@NonNull String deviceId) {
        this.deviceId = deviceId;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("deviceId", deviceId == null ? JSONObject.NULL : deviceId);
        return result;
    }
}
