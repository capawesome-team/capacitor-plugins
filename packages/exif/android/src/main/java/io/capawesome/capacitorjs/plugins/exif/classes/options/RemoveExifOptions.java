package io.capawesome.capacitorjs.plugins.exif.classes.options;

import android.net.Uri;
import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.exif.classes.CustomExceptions;

public class RemoveExifOptions {

    private final boolean keepOrientation;

    @NonNull
    private final String path;

    public RemoveExifOptions(@NonNull PluginCall call) throws Exception {
        this.keepOrientation = call.getBoolean("keepOrientation", true);
        this.path = RemoveExifOptions.getPathFromCall(call);
    }

    public boolean getKeepOrientation() {
        return keepOrientation;
    }

    @NonNull
    public String getPath() {
        return path;
    }

    @NonNull
    private static String getPathFromCall(@NonNull PluginCall call) throws Exception {
        String path = call.getString("path");
        if (path == null || path.isEmpty()) {
            throw CustomExceptions.PATH_MISSING;
        }
        if (path.startsWith("file://")) {
            String uriPath = Uri.parse(path).getPath();
            if (uriPath != null) {
                return uriPath;
            }
        }
        return path;
    }
}
