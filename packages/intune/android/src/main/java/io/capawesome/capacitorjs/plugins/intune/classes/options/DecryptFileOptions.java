package io.capawesome.capacitorjs.plugins.intune.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.intune.IntuneHelper;
import io.capawesome.capacitorjs.plugins.intune.classes.CustomExceptions;

public class DecryptFileOptions {

    @Nullable
    private final String destination;

    @NonNull
    private final String path;

    public DecryptFileOptions(@NonNull PluginCall call) throws Exception {
        this.destination = DecryptFileOptions.getDestinationFromCall(call);
        this.path = DecryptFileOptions.getPathFromCall(call);
    }

    @Nullable
    public String getDestination() {
        return destination;
    }

    @NonNull
    public String getPath() {
        return path;
    }

    @Nullable
    private static String getDestinationFromCall(@NonNull PluginCall call) {
        String destination = call.getString("destination");
        if (destination == null) {
            return null;
        }
        return IntuneHelper.getFilePath(destination);
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
