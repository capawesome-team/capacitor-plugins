package io.capawesome.capacitorjs.plugins.inappbrowser.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class ClearCookiesOptions {

    @Nullable
    private final String url;

    public ClearCookiesOptions(@NonNull PluginCall call) {
        String url = call.getString("url");
        this.url = url == null || url.isEmpty() ? null : url;
    }

    @Nullable
    public String getUrl() {
        return url;
    }
}
