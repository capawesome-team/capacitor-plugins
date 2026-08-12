package io.capawesome.capacitorjs.plugins.photomanipulator.classes.options;

import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.photomanipulator.classes.CustomExceptions;
import org.json.JSONException;

public class TransformOptions {

    public static final String FORMAT_JPEG = "JPEG";
    public static final String FORMAT_PNG = "PNG";
    public static final String FORMAT_WEBP = "WEBP";

    @Nullable
    private final Rect crop;

    private final boolean flipHorizontal;

    private final boolean flipVertical;

    @NonNull
    private final String format;

    @NonNull
    private final String path;

    private final double quality;

    @Nullable
    private final Integer resizeHeight;

    @Nullable
    private final Integer resizeWidth;

    private final int rotate;

    public TransformOptions(@NonNull PluginCall call) throws Exception {
        this.crop = TransformOptions.getCropFromCall(call);
        this.flipHorizontal = TransformOptions.getFlipHorizontalFromCall(call);
        this.flipVertical = TransformOptions.getFlipVerticalFromCall(call);
        this.format = TransformOptions.getFormatFromCall(call);
        this.path = TransformOptions.getPathFromCall(call);
        this.quality = TransformOptions.getQualityFromCall(call);
        JSObject resize = TransformOptions.getResizeFromCall(call);
        this.resizeHeight = TransformOptions.getResizeHeight(resize);
        this.resizeWidth = TransformOptions.getResizeWidth(resize);
        this.rotate = TransformOptions.getRotateFromCall(call);
    }

    @Nullable
    public Rect getCrop() {
        return crop;
    }

    @NonNull
    public String getFormat() {
        return format;
    }

    @NonNull
    public String getPath() {
        return path;
    }

    public double getQuality() {
        return quality;
    }

    @Nullable
    public Integer getResizeHeight() {
        return resizeHeight;
    }

    @Nullable
    public Integer getResizeWidth() {
        return resizeWidth;
    }

    public int getRotate() {
        return rotate;
    }

    public boolean isFlipHorizontal() {
        return flipHorizontal;
    }

    public boolean isFlipVertical() {
        return flipVertical;
    }

    @Nullable
    private static Rect getCropFromCall(@NonNull PluginCall call) throws Exception {
        JSObject crop = call.getObject("crop", null);
        if (crop == null) {
            return null;
        }
        int height;
        int width;
        int x;
        int y;
        try {
            height = crop.getInt("height");
            width = crop.getInt("width");
            x = crop.getInt("x");
            y = crop.getInt("y");
        } catch (JSONException exception) {
            throw CustomExceptions.CROP_INVALID;
        }
        if (x < 0 || y < 0 || width <= 0 || height <= 0) {
            throw CustomExceptions.CROP_INVALID;
        }
        return new Rect(x, y, x + width, y + height);
    }

    private static boolean getFlipHorizontalFromCall(@NonNull PluginCall call) {
        return Boolean.TRUE.equals(call.getBoolean("flipHorizontal", false));
    }

    private static boolean getFlipVerticalFromCall(@NonNull PluginCall call) {
        return Boolean.TRUE.equals(call.getBoolean("flipVertical", false));
    }

    @NonNull
    private static String getFormatFromCall(@NonNull PluginCall call) throws Exception {
        String format = call.getString("format", FORMAT_JPEG);
        switch (format) {
            case FORMAT_JPEG:
            case FORMAT_PNG:
            case FORMAT_WEBP:
                return format;
            default:
                throw CustomExceptions.FORMAT_INVALID;
        }
    }

    @NonNull
    private static String getPathFromCall(@NonNull PluginCall call) throws Exception {
        String path = call.getString("path");
        if (path == null) {
            throw CustomExceptions.PATH_MISSING;
        }
        return path;
    }

    private static double getQualityFromCall(@NonNull PluginCall call) throws Exception {
        double quality = call.getDouble("quality", 0.9);
        if (quality < 0 || quality > 1) {
            throw CustomExceptions.QUALITY_INVALID;
        }
        return quality;
    }

    @Nullable
    private static JSObject getResizeFromCall(@NonNull PluginCall call) throws Exception {
        JSObject resize = call.getObject("resize", null);
        if (resize == null) {
            return null;
        }
        if (!resize.has("width") && !resize.has("height")) {
            throw CustomExceptions.RESIZE_INVALID;
        }
        return resize;
    }

    @Nullable
    private static Integer getResizeHeight(@Nullable JSObject resize) throws Exception {
        if (resize == null || !resize.has("height")) {
            return null;
        }
        int height = resize.optInt("height");
        if (height <= 0) {
            throw CustomExceptions.RESIZE_INVALID;
        }
        return height;
    }

    @Nullable
    private static Integer getResizeWidth(@Nullable JSObject resize) throws Exception {
        if (resize == null || !resize.has("width")) {
            return null;
        }
        int width = resize.optInt("width");
        if (width <= 0) {
            throw CustomExceptions.RESIZE_INVALID;
        }
        return width;
    }

    private static int getRotateFromCall(@NonNull PluginCall call) throws Exception {
        int rotate = call.getInt("rotate", 0);
        if (rotate != 0 && rotate != 90 && rotate != 180 && rotate != 270) {
            throw CustomExceptions.ROTATE_INVALID;
        }
        return rotate;
    }
}
