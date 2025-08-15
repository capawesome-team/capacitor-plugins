package io.capawesome.capacitorjs.plugins.filepicker;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;
import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import com.getcapacitor.Logger;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public class FilePicker {

    public static final String TAG = "FilePicker";
    private final FilePickerPlugin plugin;

    FilePicker(FilePickerPlugin plugin) {
        this.plugin = plugin;
    }

    public void copyFile(@NonNull Uri from, @NonNull Uri to, Boolean shouldOverwrite) throws Exception {
        if (!shouldOverwrite) {
            File file = new File(Objects.requireNonNull(to.getPath()));
            if (!file.exists()) {
                throw new Exception(FilePickerPlugin.ERROR_FILE_ALREADY_EXISTS);
            }
        }
        InputStream inputStream = plugin.getBridge().getContext().getContentResolver().openInputStream(from);
        OutputStream outputStream = plugin.getBridge().getContext().getContentResolver().openOutputStream(to);
        if (inputStream == null || outputStream == null) {
            throw new Exception(FilePickerPlugin.ERROR_COPY_FILE_FAILED);
        }
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.close();
        inputStream.close();
    }

    public String getPathFromUri(@NonNull Uri uri) {
        return uri.toString();
    }

    public Uri getUriByPath(@NonNull String path) {
        Uri uri = Uri.parse(path);
        if (uri.getScheme() != null && uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            return uri;
        } else if (uri.getScheme() == null || uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            return FileProvider.getUriForFile(
                plugin.getActivity(),
                plugin.getContext().getPackageName() + ".fileprovider",
                new File(uri.getPath())
            );
        } else {
            return FileProvider.getUriForFile(plugin.getActivity(), plugin.getContext().getPackageName() + ".fileprovider", new File(path));
        }
    }

    public String getNameFromUri(@NonNull Uri uri) {
        String displayName = "";
        String[] projection = { OpenableColumns.DISPLAY_NAME };
        Cursor cursor = plugin.getBridge().getContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIdx = cursor.getColumnIndex(projection[0]);
            displayName = cursor.getString(columnIdx);
            cursor.close();
        }
        if (displayName == null || displayName.length() < 1) {
            displayName = uri.getLastPathSegment();
        }
        return displayName;
    }

    public String getDataFromUri(@NonNull Uri uri) {
        try {
            InputStream stream = plugin.getBridge().getActivity().getContentResolver().openInputStream(uri);
            byte[] bytes = getBytesFromInputStream(stream);
            return Base64.encodeToString(bytes, Base64.NO_WRAP);
        } catch (FileNotFoundException e) {
            Logger.error(TAG, "openInputStream failed.", e);
        } catch (IOException e) {
            Logger.error(TAG, "getBytesFromInputStream failed.", e);
        }
        return "";
    }

    @Nullable
    public String getMimeTypeFromUri(@NonNull Uri uri) {
        return plugin.getBridge().getContext().getContentResolver().getType(uri);
    }

    @Nullable
    public Long getModifiedAtFromUri(@NonNull Uri uri) {
        try {
            long modifiedAt = 0;
            Cursor cursor = plugin.getBridge().getContext().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIdx = cursor.getColumnIndex(DocumentsContract.Document.COLUMN_LAST_MODIFIED);
                modifiedAt = cursor.getLong(columnIdx);
                cursor.close();
            }
            return modifiedAt;
        } catch (Exception e) {
            Logger.error(TAG, "getModifiedAtFromUri failed.", e);
            return null;
        }
    }

    public long getSizeFromUri(@NonNull Uri uri) {
        long size = 0;
        String[] projection = { OpenableColumns.SIZE };
        Cursor cursor = plugin.getBridge().getContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIdx = cursor.getColumnIndex(projection[0]);
            size = cursor.getLong(columnIdx);
            cursor.close();
        }
        return size;
    }

    @Nullable
    public Long getDurationFromUri(@NonNull Uri uri) {
        if (isVideoUri(uri)) {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(plugin.getBridge().getContext(), uri);
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            long durationMs = Long.parseLong(time);
            try {
                retriever.release();
            } catch (Exception e) {
                Logger.error(TAG, "MediaMetadataRetriever.release() failed.", e);
            }
            return durationMs / 1000l;
        }
        return null;
    }

    @Nullable
    public FileResolution getHeightAndWidthFromUri(@NonNull Uri uri) {
        if (isImageUri(uri)) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            try {
                BitmapFactory.decodeStream(plugin.getBridge().getContext().getContentResolver().openInputStream(uri), null, options);
                return new FileResolution(options.outHeight, options.outWidth);
            } catch (FileNotFoundException exception) {
                exception.printStackTrace();
                return null;
            }
        } else if (isVideoUri(uri)) {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(plugin.getBridge().getContext(), uri);
            int width = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
            int height = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
            try {
                retriever.release();
            } catch (Exception e) {
                Logger.error(TAG, "MediaMetadataRetriever.release() failed.", e);
            }
            return new FileResolution(height, width);
        }
        return null;
    }

    private boolean isImageUri(Uri uri) {
        String mimeType = getMimeTypeFromUri(uri);
        if (mimeType == null) {
            return false;
        }
        return mimeType.startsWith("image");
    }

    private boolean isVideoUri(Uri uri) {
        String mimeType = getMimeTypeFromUri(uri);
        if (mimeType == null) {
            return false;
        }
        return mimeType.startsWith("video");
    }

    /**
     * Source: https://stackoverflow.com/a/17861016/16289814
     */
    private static byte[] getBytesFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[0xFFFF];
        for (int len = is.read(buffer); len != -1; len = is.read(buffer)) {
            os.write(buffer, 0, len);
        }
        return os.toByteArray();
    }
}
