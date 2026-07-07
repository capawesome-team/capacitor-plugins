package io.capawesome.capacitorjs.plugins.alarm.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.alarm.interfaces.Result;
import org.json.JSONObject;

public class CreateAlarmResult implements Result {

    @Nullable
    private final String id;

    public CreateAlarmResult(@Nullable String id) {
        this.id = id;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("id", id == null ? JSONObject.NULL : id);
        return result;
    }
}
