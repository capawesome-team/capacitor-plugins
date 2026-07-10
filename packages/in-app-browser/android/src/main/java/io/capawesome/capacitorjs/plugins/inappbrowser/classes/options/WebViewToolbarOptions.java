package io.capawesome.capacitorjs.plugins.inappbrowser.classes.options;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.CustomExceptions;

public class WebViewToolbarOptions {

    @Nullable
    private final Integer backgroundColor;

    @NonNull
    private final String closeButtonText;

    @Nullable
    private final Integer color;

    private final boolean showNavigationButtons;
    private final boolean showUrl;

    @Nullable
    private final String title;

    private final boolean visible;

    public WebViewToolbarOptions(@Nullable JSObject object) throws Exception {
        this.backgroundColor = WebViewToolbarOptions.getColorFromObject(
            object,
            "backgroundColor",
            CustomExceptions.BACKGROUND_COLOR_INVALID
        );
        this.closeButtonText = object == null ? "Close" : object.optString("closeButtonText", "Close");
        this.color = WebViewToolbarOptions.getColorFromObject(object, "color", CustomExceptions.COLOR_INVALID);
        this.showNavigationButtons = object != null && object.optBoolean("showNavigationButtons", false);
        this.showUrl = object != null && object.optBoolean("showUrl", false);
        this.title = object == null ? null : object.getString("title");
        this.visible = object == null || object.optBoolean("visible", true);
    }

    @Nullable
    public Integer getBackgroundColor() {
        return backgroundColor;
    }

    @NonNull
    public String getCloseButtonText() {
        return closeButtonText;
    }

    @Nullable
    public Integer getColor() {
        return color;
    }

    public boolean getShowNavigationButtons() {
        return showNavigationButtons;
    }

    public boolean getShowUrl() {
        return showUrl;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public boolean getVisible() {
        return visible;
    }

    @Nullable
    private static Integer getColorFromObject(@Nullable JSObject object, @NonNull String key, @NonNull Exception invalidException)
        throws Exception {
        String color = object == null ? null : object.getString(key);
        if (color == null) {
            return null;
        }
        try {
            return Color.parseColor(color);
        } catch (IllegalArgumentException exception) {
            throw invalidException;
        }
    }
}
