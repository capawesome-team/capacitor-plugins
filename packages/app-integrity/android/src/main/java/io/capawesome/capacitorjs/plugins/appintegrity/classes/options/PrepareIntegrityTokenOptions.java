package io.capawesome.capacitorjs.plugins.appintegrity.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.appintegrity.classes.CustomExceptions;

public class PrepareIntegrityTokenOptions {

    private final long cloudProjectNumber;

    public PrepareIntegrityTokenOptions(@NonNull PluginCall call) throws Exception {
        this.cloudProjectNumber = PrepareIntegrityTokenOptions.getCloudProjectNumberFromCall(call);
    }

    public long getCloudProjectNumber() {
        return cloudProjectNumber;
    }

    private static long getCloudProjectNumberFromCall(@NonNull PluginCall call) throws Exception {
        Object value = call.getData().opt("cloudProjectNumber");
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        throw CustomExceptions.CLOUD_PROJECT_NUMBER_MISSING;
    }
}
