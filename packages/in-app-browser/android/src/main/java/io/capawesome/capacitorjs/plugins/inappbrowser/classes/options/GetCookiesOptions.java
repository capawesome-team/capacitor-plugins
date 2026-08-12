package io.capawesome.capacitorjs.plugins.inappbrowser.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.CustomExceptions;

public class GetCookiesOptions {

    @NonNull
    private final String url;

    public GetCookiesOptions(@NonNull PluginCall call) throws Exception {
        this.url = GetCookiesOptions.getUrlFromCall(call);
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    @NonNull
    private static String getUrlFromCall(@NonNull PluginCall call) throws Exception {
        String url = call.getString("url");
        if (url == null || url.isEmpty()) {
            throw CustomExceptions.URL_MISSING;
        }
        return url;
    }
}
