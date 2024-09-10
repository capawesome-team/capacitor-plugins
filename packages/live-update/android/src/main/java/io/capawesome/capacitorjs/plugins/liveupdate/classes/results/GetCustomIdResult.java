package io.capawesome.capacitorjs.plugins.liveupdate.classes.results;

import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;
import org.json.JSONObject;

public class GetCustomIdResult implements Result {

    @Nullable
    private String customId;

    public GetCustomIdResult(@Nullable String customId) {
        this.customId = customId;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("customId", customId == null ? JSONObject.NULL : customId);
        return result;
    }
}
