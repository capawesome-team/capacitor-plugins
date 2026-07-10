package io.capawesome.capacitorjs.plugins.inappbrowser.classes.options;

import android.net.Uri;
import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.CustomExceptions;

public class OpenInExternalBrowserOptions {

    @NonNull
    private final Uri uri;

    public OpenInExternalBrowserOptions(@NonNull PluginCall call) throws Exception {
        this.uri = OpenInExternalBrowserOptions.getUriFromCall(call);
    }

    @NonNull
    public Uri getUri() {
        return uri;
    }

    @NonNull
    private static Uri getUriFromCall(@NonNull PluginCall call) throws Exception {
        String url = call.getString("url");
        if (url == null || url.isEmpty()) {
            throw CustomExceptions.URL_MISSING;
        }
        return Uri.parse(url);
    }
}
