package io.capawesome.capacitorjs.plugins.intune.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.intune.IntuneHelper;
import io.capawesome.capacitorjs.plugins.intune.classes.CustomExceptions;

public class IsFileEncryptedOptions {

    @NonNull
    private final String path;

    public IsFileEncryptedOptions(@NonNull PluginCall call) throws Exception {
        this.path = IsFileEncryptedOptions.getPathFromCall(call);
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
        return IntuneHelper.getFilePath(path);
    }
}
