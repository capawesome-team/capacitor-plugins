package io.capawesome.capacitorjs.plugins.androidintentlauncher.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.androidintentlauncher.interfaces.Result;
import org.json.JSONObject;

public class StartActivityResult implements Result {

    @Nullable
    private final String dataUri;

    private final int resultCode;

    public StartActivityResult(int resultCode, @Nullable String dataUri) {
        this.resultCode = resultCode;
        this.dataUri = dataUri;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("resultCode", resultCode);
        result.put("dataUri", dataUri == null ? JSONObject.NULL : dataUri);
        return result;
    }
}
