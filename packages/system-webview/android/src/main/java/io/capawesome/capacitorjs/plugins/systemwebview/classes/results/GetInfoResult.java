package io.capawesome.capacitorjs.plugins.systemwebview.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.systemwebview.interfaces.Result;
import org.json.JSONObject;

public class GetInfoResult implements Result {

    @Nullable
    private final Integer majorVersion;

    @NonNull
    private final String packageName;

    @NonNull
    private final String versionName;

    public GetInfoResult(@NonNull String packageName, @NonNull String versionName, @Nullable Integer majorVersion) {
        this.packageName = packageName;
        this.versionName = versionName;
        this.majorVersion = majorVersion;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("packageName", packageName);
        result.put("versionName", versionName);
        result.put("majorVersion", majorVersion == null ? JSONObject.NULL : majorVersion);
        return result;
    }
}
