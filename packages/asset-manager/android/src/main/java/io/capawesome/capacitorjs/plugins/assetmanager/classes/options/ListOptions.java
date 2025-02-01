package io.capawesome.capacitorjs.plugins.assetmanager.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class ListOptions {

    @Nullable
    private final String path;

    public ListOptions(@NonNull PluginCall call) throws Exception {
        this.path = call.getString("path", "/");
    }

    @NonNull
    public String getPath() {
        return path;
    }
}
