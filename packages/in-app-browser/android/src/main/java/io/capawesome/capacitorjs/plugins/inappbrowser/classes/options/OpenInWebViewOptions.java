package io.capawesome.capacitorjs.plugins.inappbrowser.classes.options;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.CustomExceptions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OpenInWebViewOptions {

    private final boolean allowZoom;
    private final boolean hardwareBackButton;

    @NonNull
    private final Map<String, String> headers;

    private final boolean mediaPlaybackRequiresUserAction;
    private final boolean pauseMediaWhenHidden;

    @NonNull
    private final WebViewToolbarOptions toolbar;

    @NonNull
    private final Uri uri;

    @Nullable
    private final String userAgent;

    public OpenInWebViewOptions(@NonNull PluginCall call) throws Exception {
        JSObject androidOptions = call.getObject("android");
        this.allowZoom = androidOptions != null && androidOptions.optBoolean("allowZoom", false);
        this.hardwareBackButton = androidOptions == null || androidOptions.optBoolean("hardwareBackButton", true);
        this.headers = OpenInWebViewOptions.getHeadersFromCall(call);
        this.mediaPlaybackRequiresUserAction = call.getBoolean("mediaPlaybackRequiresUserAction", false);
        this.pauseMediaWhenHidden = androidOptions == null || androidOptions.optBoolean("pauseMediaWhenHidden", true);
        this.toolbar = new WebViewToolbarOptions(call.getObject("toolbar"));
        this.uri = OpenInWebViewOptions.getUriFromCall(call);
        this.userAgent = call.getString("userAgent");
    }

    public boolean getAllowZoom() {
        return allowZoom;
    }

    public boolean getHardwareBackButton() {
        return hardwareBackButton;
    }

    @NonNull
    public Map<String, String> getHeaders() {
        return headers;
    }

    public boolean getMediaPlaybackRequiresUserAction() {
        return mediaPlaybackRequiresUserAction;
    }

    public boolean getPauseMediaWhenHidden() {
        return pauseMediaWhenHidden;
    }

    @NonNull
    public WebViewToolbarOptions getToolbar() {
        return toolbar;
    }

    @NonNull
    public Uri getUri() {
        return uri;
    }

    @Nullable
    public String getUserAgent() {
        return userAgent;
    }

    @NonNull
    private static Map<String, String> getHeadersFromCall(@NonNull PluginCall call) {
        Map<String, String> headers = new HashMap<>();
        JSObject headersObject = call.getObject("headers");
        if (headersObject == null) {
            return headers;
        }
        Iterator<String> keys = headersObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            String value = headersObject.getString(key);
            if (value != null) {
                headers.put(key, value);
            }
        }
        return headers;
    }

    @NonNull
    private static Uri getUriFromCall(@NonNull PluginCall call) throws Exception {
        String url = call.getString("url");
        if (url == null || url.isEmpty()) {
            throw CustomExceptions.URL_MISSING;
        }
        Uri uri = Uri.parse(url);
        String scheme = uri.getScheme();
        if (scheme == null || !(scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https"))) {
            throw CustomExceptions.URL_INVALID;
        }
        return uri;
    }
}
