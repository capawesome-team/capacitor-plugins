package io.capawesome.capacitorjs.plugins.applauncher.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.applauncher.classes.CustomExceptions;

public class OpenUrlOptions {

    @NonNull
    private final String url;

    public OpenUrlOptions(@NonNull PluginCall call) throws Exception {
        String url = call.getString("url");
        if (url == null) {
            throw CustomExceptions.URL_MISSING;
        }
        this.url = url;
    }

    @NonNull
    public String getUrl() {
        return url;
    }
}
