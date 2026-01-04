package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.interfaces.Result;

public class GetSettingsResult implements Result {

    @NonNull
    private final String version;

    @NonNull
    private final String environment;

    public GetSettingsResult(@NonNull String version, @NonNull String environment) {
        this.version = version;
        this.environment = environment;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("version", version);
        result.put("environment", environment);
        return result;
    }
}
