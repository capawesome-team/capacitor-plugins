package io.capawesome.capacitorjs.plugins.photomanipulator;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;
import io.capawesome.capacitorjs.plugins.photomanipulator.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.photomanipulator.classes.options.GetInfoOptions;
import io.capawesome.capacitorjs.plugins.photomanipulator.classes.options.TransformOptions;
import io.capawesome.capacitorjs.plugins.photomanipulator.classes.results.GetInfoResult;
import io.capawesome.capacitorjs.plugins.photomanipulator.classes.results.TransformResult;
import io.capawesome.capacitorjs.plugins.photomanipulator.interfaces.NonEmptyResultCallback;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PhotoManipulator {

    private static final String IMAGES_DIRECTORY_NAME = "capawesome_capacitor_photo_manipulator_images";

    @NonNull
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @NonNull
    private final PhotoManipulatorPlugin plugin;

    public PhotoManipulator(@NonNull PhotoManipulatorPlugin plugin) {
        this.plugin = plugin;
        executor.execute(this::cleanUpImagesDirectory);
    }

    public void getInfo(@NonNull GetInfoOptions options, @NonNull NonEmptyResultCallback<GetInfoResult> callback) {
        executor.execute(() -> {
            try {
                callback.success(fetchInfo(options));
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void transform(@NonNull TransformOptions options, @NonNull NonEmptyResultCallback<TransformResult> callback) {
        executor.execute(() -> {
            try {
                callback.success(transformImage(options));
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    @NonNull
    private Bitmap applyTransformations(
        @NonNull Bitmap bitmap,
        int sourceWidth,
        int sourceHeight,
        @NonNull Rect crop,
        @Nullable Size targetSize,
        @NonNull TransformOptions options
    ) {
        Bitmap result = bitmap;
        double scaleX = (double) result.getWidth() / sourceWidth;
        double scaleY = (double) result.getHeight() / sourceHeight;
        int x = Math.min((int) Math.round(crop.left * scaleX), result.getWidth() - 1);
        int y = Math.min((int) Math.round(crop.top * scaleY), result.getHeight() - 1);
        int width = Math.min(Math.max(1, (int) Math.round(crop.width() * scaleX)), result.getWidth() - x);
        int height = Math.min(Math.max(1, (int) Math.round(crop.height() * scaleY)), result.getHeight() - y);
        if (x != 0 || y != 0 || width != result.getWidth() || height != result.getHeight()) {
            result = Bitmap.createBitmap(result, x, y, width, height);
        }
        Matrix matrix = new Matrix();
        if (targetSize != null) {
            matrix.postScale((float) targetSize.getWidth() / result.getWidth(), (float) targetSize.getHeight() / result.getHeight());
        }
        if (options.getRotate() != 0) {
            matrix.postRotate(options.getRotate());
        }
        if (options.isFlipHorizontal()) {
            matrix.postScale(-1, 1);
        }
        if (options.isFlipVertical()) {
            matrix.postScale(1, -1);
        }
        if (!matrix.isIdentity()) {
            Bitmap transformed = Bitmap.createBitmap(result, 0, 0, result.getWidth(), result.getHeight(), matrix, true);
            if (result != bitmap && transformed != result) {
                result.recycle();
            }
            result = transformed;
        }
        return result;
    }

    private int calculateSampleSize(int cropWidth, int cropHeight, @Nullable Size targetSize) {
        if (targetSize == null) {
            return 1;
        }
        int sampleSize = 1;
        while (cropWidth / (sampleSize * 2) >= targetSize.getWidth() && cropHeight / (sampleSize * 2) >= targetSize.getHeight()) {
            sampleSize *= 2;
        }
        return sampleSize;
    }

    private void cleanUpImagesDirectory() {
        File[] files = getImagesDirectory().listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            file.delete();
        }
    }

    @NonNull
    private Bitmap decodeBitmap(@NonNull File file, int sampleSize, int rotationDegrees, boolean flipped) throws Exception {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            try {
                ImageDecoder.Source source = ImageDecoder.createSource(file);
                return ImageDecoder.decodeBitmap(source, (decoder, info, src) -> {
                    decoder.setTargetSampleSize(sampleSize);
                    decoder.setAllocator(ImageDecoder.ALLOCATOR_SOFTWARE);
                });
            } catch (IOException exception) {
                throw CustomExceptions.UNSUPPORTED_FORMAT;
            }
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        if (bitmap == null) {
            throw CustomExceptions.UNSUPPORTED_FORMAT;
        }
        if (rotationDegrees == 0 && !flipped) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        if (flipped) {
            matrix.postScale(-1, 1);
        }
        if (rotationDegrees != 0) {
            matrix.postRotate(rotationDegrees);
        }
        Bitmap oriented = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (oriented != bitmap) {
            bitmap.recycle();
        }
        return oriented;
    }

    @NonNull
    private BitmapFactory.Options decodeBounds(@NonNull File file) throws Exception {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        if (options.outWidth <= 0 || options.outHeight <= 0) {
            throw CustomExceptions.UNSUPPORTED_FORMAT;
        }
        return options;
    }

    @NonNull
    private File encodeBitmap(@NonNull Bitmap bitmap, @NonNull String format, double quality) throws Exception {
        Bitmap.CompressFormat compressFormat;
        String extension;
        switch (format) {
            case TransformOptions.FORMAT_PNG:
                compressFormat = Bitmap.CompressFormat.PNG;
                extension = "png";
                break;
            case TransformOptions.FORMAT_WEBP:
                compressFormat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
                    ? Bitmap.CompressFormat.WEBP_LOSSY
                    : Bitmap.CompressFormat.WEBP;
                extension = "webp";
                break;
            default:
                compressFormat = Bitmap.CompressFormat.JPEG;
                extension = "jpeg";
        }
        File outputFile = new File(getImagesDirectory(), UUID.randomUUID() + "." + extension);
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            if (!bitmap.compress(compressFormat, (int) Math.round(quality * 100), outputStream)) {
                throw CustomExceptions.TRANSFORM_FAILED;
            }
        }
        return outputFile;
    }

    @NonNull
    private GetInfoResult fetchInfo(@NonNull GetInfoOptions options) throws Exception {
        File file = getFileByPath(options.getPath());
        BitmapFactory.Options bounds = decodeBounds(file);
        int rotationDegrees = getExifRotationDegrees(file);
        boolean swapDimensions = rotationDegrees == 90 || rotationDegrees == 270;
        String format = getFormatFromMimeType(bounds.outMimeType);
        int height = swapDimensions ? bounds.outWidth : bounds.outHeight;
        int width = swapDimensions ? bounds.outHeight : bounds.outWidth;
        return new GetInfoResult(format, height, width);
    }

    private int getExifRotationDegrees(@NonNull File file) {
        try {
            return new ExifInterface(file).getRotationDegrees();
        } catch (IOException exception) {
            return 0;
        }
    }

    @NonNull
    private File getFileByPath(@NonNull String path) throws Exception {
        Uri uri = Uri.parse(path);
        String scheme = uri.getScheme();
        File file;
        if (scheme == null) {
            file = new File(path);
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            String uriPath = uri.getPath();
            if (uriPath == null) {
                throw CustomExceptions.PATH_INVALID;
            }
            file = new File(uriPath);
        } else {
            throw CustomExceptions.PATH_INVALID;
        }
        if (!file.exists()) {
            throw CustomExceptions.FILE_NOT_FOUND;
        }
        return file;
    }

    @Nullable
    private String getFormatFromMimeType(@Nullable String mimeType) {
        if (mimeType == null || !mimeType.startsWith("image/")) {
            return null;
        }
        String format = mimeType.substring("image/".length()).toLowerCase(Locale.ROOT);
        return format.equals("jpg") ? "jpeg" : format;
    }

    @NonNull
    private File getImagesDirectory() {
        File directory = new File(plugin.getContext().getCacheDir(), IMAGES_DIRECTORY_NAME);
        directory.mkdirs();
        return directory;
    }

    @Nullable
    private Size getTargetSize(@NonNull Rect crop, @Nullable Integer resizeWidth, @Nullable Integer resizeHeight) {
        if (resizeWidth == null && resizeHeight == null) {
            return null;
        }
        if (resizeWidth != null && resizeHeight != null) {
            return new Size(resizeWidth, resizeHeight);
        }
        if (resizeWidth != null) {
            return new Size(resizeWidth, Math.max(1, Math.round(((float) crop.height() * resizeWidth) / crop.width())));
        }
        return new Size(Math.max(1, Math.round(((float) crop.width() * resizeHeight) / crop.height())), resizeHeight);
    }

    private boolean isExifFlipped(@NonNull File file) {
        try {
            return new ExifInterface(file).isFlipped();
        } catch (IOException exception) {
            return false;
        }
    }

    @NonNull
    private TransformResult transformImage(@NonNull TransformOptions options) throws Exception {
        File file = getFileByPath(options.getPath());
        BitmapFactory.Options bounds = decodeBounds(file);
        int rotationDegrees = getExifRotationDegrees(file);
        boolean swapDimensions = rotationDegrees == 90 || rotationDegrees == 270;
        int sourceWidth = swapDimensions ? bounds.outHeight : bounds.outWidth;
        int sourceHeight = swapDimensions ? bounds.outWidth : bounds.outHeight;
        Rect crop = options.getCrop();
        if (crop == null) {
            crop = new Rect(0, 0, sourceWidth, sourceHeight);
        } else if (crop.right > sourceWidth || crop.bottom > sourceHeight) {
            throw CustomExceptions.CROP_INVALID;
        }
        Size targetSize = getTargetSize(crop, options.getResizeWidth(), options.getResizeHeight());
        int sampleSize = calculateSampleSize(crop.width(), crop.height(), targetSize);
        Bitmap bitmap = decodeBitmap(file, sampleSize, rotationDegrees, isExifFlipped(file));
        try {
            Bitmap transformed = applyTransformations(bitmap, sourceWidth, sourceHeight, crop, targetSize, options);
            try {
                File outputFile = encodeBitmap(transformed, options.getFormat(), options.getQuality());
                return new TransformResult(outputFile, transformed.getHeight(), transformed.getWidth());
            } finally {
                if (transformed != bitmap) {
                    transformed.recycle();
                }
            }
        } finally {
            bitmap.recycle();
        }
    }
}
