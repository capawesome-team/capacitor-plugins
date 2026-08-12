package io.capawesome.capacitorjs.plugins.photomanipulator.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.photomanipulator.classes.CustomExceptions;

public class GetInfoOptions {

    @NonNull
    private final String path;

    public GetInfoOptions(@NonNull PluginCall call) throws Exception {
        this.path = GetInfoOptions.getPathFromCall(call);
    }

    @NonNull
    public String getPath() {
        return path;
    }

    @NonNull
    private static String getPathFromCall(@NonNull PluginCall call) throws Exception {
        String path = call.getString("path");
        if (path == null) {
            throw CustomExceptions.PATH_MISSING;
        }
        return path;
    }
}
