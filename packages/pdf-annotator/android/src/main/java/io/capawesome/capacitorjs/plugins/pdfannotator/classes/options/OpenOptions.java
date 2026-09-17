package io.capawesome.capacitorjs.plugins.pdfannotator.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.pdfannotator.classes.CustomExceptions;

public class OpenOptions {

    @NonNull
    private final String path;

    public OpenOptions(@NonNull PluginCall call) throws Exception {
        this.path = OpenOptions.getPathFromCall(call);
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
