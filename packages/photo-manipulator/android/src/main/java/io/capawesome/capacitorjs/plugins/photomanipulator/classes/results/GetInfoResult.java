package io.capawesome.capacitorjs.plugins.photomanipulator.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.photomanipulator.interfaces.Result;
import org.json.JSONObject;

public class GetInfoResult implements Result {

    @Nullable
    private final String format;

    private final int height;

    private final int width;

    public GetInfoResult(@Nullable String format, int height, int width) {
        this.format = format;
        this.height = height;
        this.width = width;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("format", format == null ? JSONObject.NULL : format);
        result.put("height", height);
        result.put("width", width);
        return result;
    }
}
