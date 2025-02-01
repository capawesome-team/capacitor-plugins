package io.capawesome.capacitorjs.plugins.assetmanager.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.assetmanager.AssetManagerPlugin;

public class ReadOptions {

    @Nullable
    private final String encoding;

    @NonNull
    private final String path;

    public ReadOptions(@NonNull PluginCall call) throws Exception {
        this.encoding = call.getString("encoding");
        this.path = getPathFromCall(call);
    }

    @Nullable
    public String getEncoding() {
        return encoding;
    }

    @NonNull
    public String getPath() {
        return path;
    }

    @NonNull
    private String getPathFromCall(@NonNull PluginCall call) throws Exception {
        String path = call.getString("path");
        if (path == null) {
            throw new Exception(AssetManagerPlugin.ERROR_PATH_MISSING);
        }
        return path;
    }
}
