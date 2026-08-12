package io.capawesome.capacitorjs.plugins.inappbrowser.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.inappbrowser.interfaces.Result;
import java.util.Map;

public class GetCookiesResult implements Result {

    @NonNull
    private final Map<String, String> cookies;

    public GetCookiesResult(@NonNull Map<String, String> cookies) {
        this.cookies = cookies;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject cookies = new JSObject();
        for (Map.Entry<String, String> entry : this.cookies.entrySet()) {
            cookies.put(entry.getKey(), entry.getValue());
        }
        JSObject result = new JSObject();
        result.put("cookies", cookies);
        return result;
    }
}
