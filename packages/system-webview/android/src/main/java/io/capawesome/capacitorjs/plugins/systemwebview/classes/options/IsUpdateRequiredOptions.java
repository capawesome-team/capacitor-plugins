package io.capawesome.capacitorjs.plugins.systemwebview.classes.options;

import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.systemwebview.classes.CustomExceptions;

public class IsUpdateRequiredOptions {

    private final int minMajorVersion;

    public IsUpdateRequiredOptions(PluginCall call) throws Exception {
        Integer minMajorVersion = call.getInt("minMajorVersion");
        if (minMajorVersion == null) {
            throw CustomExceptions.MIN_MAJOR_VERSION_MISSING;
        }
        this.minMajorVersion = minMajorVersion;
    }

    public int getMinMajorVersion() {
        return minMajorVersion;
    }
}
