package io.capawesome.capacitorjs.plugins.inappbrowser.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class ClearCookiesOptions {

    @Nullable
    private final String url;

    public ClearCookiesOptions(@NonNull PluginCall call) {
        this.url = call.getString("url");
    }

    @Nullable
    public String getUrl() {
        return url;
    }
}
