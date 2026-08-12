package io.capawesome.capacitorjs.plugins.inappbrowser.classes.options;

import android.graphics.Color;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.CustomExceptions;

public class OpenInSystemBrowserOptions {

    private final boolean hideToolbarOnScroll;
    private final boolean showTitle;

    @Nullable
    private final Integer toolbarColor;

    @NonNull
    private final Uri uri;

    public OpenInSystemBrowserOptions(@NonNull PluginCall call) throws Exception {
        JSObject androidOptions = call.getObject("android");
        this.hideToolbarOnScroll = androidOptions != null && androidOptions.optBoolean("hideToolbarOnScroll", false);
        this.showTitle = androidOptions != null && androidOptions.optBoolean("showTitle", false);
        this.toolbarColor = OpenInSystemBrowserOptions.getToolbarColorFromObject(androidOptions);
        this.uri = OpenInSystemBrowserOptions.getUriFromCall(call);
    }

    public boolean getHideToolbarOnScroll() {
        return hideToolbarOnScroll;
    }

    public boolean getShowTitle() {
        return showTitle;
    }

    @Nullable
    public Integer getToolbarColor() {
        return toolbarColor;
    }

    @NonNull
    public Uri getUri() {
        return uri;
    }

    @Nullable
    private static Integer getToolbarColorFromObject(@Nullable JSObject object) throws Exception {
        String toolbarColor = object == null ? null : object.getString("toolbarColor");
        if (toolbarColor == null) {
            return null;
        }
        try {
            return Color.parseColor(toolbarColor);
        } catch (IllegalArgumentException exception) {
            throw CustomExceptions.TOOLBAR_COLOR_INVALID;
        }
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
