package io.capawesome.capacitorjs.plugins.screenshot.classes.results;

import android.util.Base64;
import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.screenshot.classes.interfaces.Result;

public class TakeResult implements Result {

    private final String image;

    public TakeResult(@NonNull byte[] bytes) {
        image = Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("uri", image);
        return result;
    }
}
