package io.capawesome.capacitorjs.plugins.exif.classes.options;

import android.net.Uri;
import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.exif.classes.CustomExceptions;

public class ReadExifOptions {

    @NonNull
    private final String path;

    public ReadExifOptions(@NonNull PluginCall call) throws Exception {
        this.path = ReadExifOptions.getPathFromCall(call);
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
