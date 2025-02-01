package io.capawesome.capacitorjs.plugins.assetmanager.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.assetmanager.AssetManagerPlugin;

public class CopyOptions {

    @NonNull
    private final String from;

    @NonNull
    private final String to;

    public CopyOptions(@NonNull PluginCall call) throws Exception {
        this.from = getFromFromCall(call);
        this.to = getToFromCall(call);
    }

    @NonNull
    public String getFrom() {
        return from;
    }

    @NonNull
    public String getTo() {
        return to;
    }

    @NonNull
    private String getFromFromCall(@NonNull PluginCall call) throws Exception {
        String from = call.getString("from");
        if (from == null) {
            throw new Exception(AssetManagerPlugin.ERROR_FROM_MISSING);
        }
        return from;
    }

    @NonNull
    private String getToFromCall(@NonNull PluginCall call) throws Exception {
        String to = call.getString("to");
        if (to == null) {
            throw new Exception(AssetManagerPlugin.ERROR_TO_MISSING);
        }
        if (to.contains("file://")) {
            to = to.replace("file://", "");
        }
        return to;
    }
}
