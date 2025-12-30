package io.capawesome.capacitorjs.plugins.superwall.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.superwall.classes.CustomExceptions;

public class HandleDeepLinkOptions {

    @NonNull
    private final String url;

    public HandleDeepLinkOptions(@NonNull PluginCall call) throws Exception {
        this.url = getUrlFromCall(call);
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    @NonNull
    private static String getUrlFromCall(@NonNull PluginCall call) throws Exception {
        String url = call.getString("url");
        if (url == null) {
            throw CustomExceptions.URL_MISSING;
        }
        return url;
    }
}
