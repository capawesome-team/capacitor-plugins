package io.capawesome.capacitorjs.plugins.maplibre.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;

/**
 * The icon of a marker. The size is defined in CSS pixels.
 */
public class MarkerIcon {

    private static final String KEY_PREFIX = "capawesome-marker-icon";

    @Nullable
    private final Double height;

    @Nullable
    private final String url;

    @Nullable
    private final Double width;

    public MarkerIcon(@Nullable String url, @Nullable Double width, @Nullable Double height) {
        this.height = height;
        this.url = url;
        this.width = width;
    }

    @NonNull
    public static MarkerIcon fromObject(@NonNull JSObject object) {
        JSObject sizeObject = object.getJSObject("iconSize");
        return new MarkerIcon(
            object.getString("iconUrl", null),
            sizeObject == null ? null : MapLibreHelper.getDouble(sizeObject, "width"),
            sizeObject == null ? null : MapLibreHelper.getDouble(sizeObject, "height")
        );
    }

    /**
     * Creates a new icon where every property of the given icon overrides the property of this icon.
     */
    @NonNull
    public MarkerIcon apply(@NonNull MarkerIcon icon) {
        boolean hasSize = icon.width != null || icon.height != null;
        return new MarkerIcon(icon.url == null ? url : icon.url, hasSize ? icon.width : width, hasSize ? icon.height : height);
    }

    @Nullable
    public Double getHeight() {
        return height;
    }

    @NonNull
    public String getKey() {
        return KEY_PREFIX + ":" + (url == null ? "default" : url) + ":" + width + "x" + height;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    @Nullable
    public Double getWidth() {
        return width;
    }

    public boolean hasSize() {
        return width != null && height != null;
    }
}
