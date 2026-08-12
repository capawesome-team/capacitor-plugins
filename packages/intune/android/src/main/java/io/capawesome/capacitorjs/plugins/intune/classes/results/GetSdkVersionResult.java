package io.capawesome.capacitorjs.plugins.intune.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.intune.interfaces.Result;
import org.json.JSONObject;

public class GetSdkVersionResult implements Result {

    @NonNull
    private final String intuneSdkVersion;

    @Nullable
    private final String msalVersion;

    public GetSdkVersionResult(@NonNull String intuneSdkVersion, @Nullable String msalVersion) {
        this.intuneSdkVersion = intuneSdkVersion;
        this.msalVersion = msalVersion;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("intuneSdkVersion", intuneSdkVersion);
        result.put("msalVersion", msalVersion == null ? JSONObject.NULL : msalVersion);
        return result;
    }
}
