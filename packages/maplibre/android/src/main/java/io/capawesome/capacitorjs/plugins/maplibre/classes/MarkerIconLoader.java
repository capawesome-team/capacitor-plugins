package io.capawesome.capacitorjs.plugins.maplibre.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.DisplayMetrics;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import io.capawesome.capacitorjs.plugins.maplibre.R;
import io.capawesome.capacitorjs.plugins.maplibre.interfaces.NonEmptyCallback;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Loads the icons of markers on a background thread and caches them by their key.
 */
public class MarkerIconLoader {

    private static final String DATA_URI_PREFIX = "data:";
    private static final int TIMEOUT_IN_MILLISECONDS = 15000;

    @NonNull
    private final HashMap<String, Bitmap> bitmaps = new HashMap<>();

    @NonNull
    private final Context context;

    @NonNull
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @NonNull
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public MarkerIconLoader(@NonNull Context context) {
        this.context = context;
    }

    /**
     * Loads the given icons and returns the loaded bitmaps by the key of their icon.
     */
    public void loadIcons(@NonNull List<MarkerIcon> icons, @NonNull NonEmptyCallback<Map<String, Bitmap>> callback) {
        executor.execute(() -> {
            try {
                Map<String, Bitmap> result = new HashMap<>();
                for (MarkerIcon icon : icons) {
                    result.put(icon.getKey(), loadIcon(icon));
                }
                mainHandler.post(() -> callback.success(result));
            } catch (Exception exception) {
                mainHandler.post(() -> callback.error(exception));
            }
        });
    }

    @Nullable
    private Bitmap createBitmapFromDataUri(@NonNull String url) throws Exception {
        int separatorIndex = url.indexOf(',');
        if (separatorIndex < 0) {
            throw CustomExceptions.ICON_LOAD_FAILED;
        }
        byte[] bytes = Base64.decode(url.substring(separatorIndex + 1), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    @Nullable
    private Bitmap createBitmapFromUrl(@NonNull String url) throws Exception {
        URLConnection connection = new URL(url).openConnection();
        connection.setConnectTimeout(TIMEOUT_IN_MILLISECONDS);
        connection.setReadTimeout(TIMEOUT_IN_MILLISECONDS);
        try (InputStream stream = connection.getInputStream()) {
            return BitmapFactory.decodeStream(stream);
        }
    }

    @NonNull
    private Bitmap createDefaultBitmap() throws Exception {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.maplibre_default_marker);
        if (drawable == null) {
            throw CustomExceptions.ICON_LOAD_FAILED;
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        bitmap.setDensity(getDensityDpi());
        return bitmap;
    }

    private int getDensityDpi() {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    @NonNull
    private Bitmap loadIcon(@NonNull MarkerIcon icon) throws Exception {
        Bitmap cachedBitmap = bitmaps.get(icon.getKey());
        if (cachedBitmap != null) {
            return cachedBitmap;
        }
        Bitmap bitmap = loadBitmap(icon.getUrl());
        if (icon.hasSize()) {
            bitmap = scaleBitmap(bitmap, icon.getWidth(), icon.getHeight());
        }
        bitmaps.put(icon.getKey(), bitmap);
        return bitmap;
    }

    @NonNull
    private Bitmap loadBitmap(@Nullable String url) throws Exception {
        if (url == null) {
            return createDefaultBitmap();
        }
        Bitmap bitmap = url.startsWith(DATA_URI_PREFIX) ? createBitmapFromDataUri(url) : createBitmapFromUrl(url);
        if (bitmap == null) {
            throw CustomExceptions.ICON_LOAD_FAILED;
        }
        // The pixels of a loaded icon are treated as CSS pixels as long as no size is provided.
        bitmap.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        return bitmap;
    }

    @NonNull
    private Bitmap scaleBitmap(@NonNull Bitmap bitmap, double width, double height) {
        float density = context.getResources().getDisplayMetrics().density;
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(
            bitmap,
            Math.max(1, Math.round((float) width * density)),
            Math.max(1, Math.round((float) height * density)),
            true
        );
        scaledBitmap.setDensity(getDensityDpi());
        return scaledBitmap;
    }
}
