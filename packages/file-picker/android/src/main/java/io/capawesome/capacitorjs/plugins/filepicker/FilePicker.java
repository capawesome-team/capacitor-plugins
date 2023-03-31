package io.capawesome.capacitorjs.plugins.filepicker;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;
import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Bridge;
import com.getcapacitor.Logger;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FilePicker {

    public static final String TAG = "FilePicker";
    private Bridge bridge;

    FilePicker(Bridge bridge) {
        this.bridge = bridge;
    }

    public String getPathFromUri(@NonNull Uri uri) {
        return uri.toString();
    }

    public String getNameFromUri(@NonNull Uri uri) {
        String displayName = "";
        String[] projection = { OpenableColumns.DISPLAY_NAME };
        Cursor cursor = bridge.getContext().getContentResolver().query(uri, projection, null, null, null);
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
            InputStream stream = bridge.getActivity().getContentResolver().openInputStream(uri);
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
        return bridge.getContext().getContentResolver().getType(uri);
    }

    @Nullable
    public Long getModifiedAtFromUri(@NonNull Uri uri) {
        try {
            long modifiedAt = 0;
            Cursor cursor = bridge.getContext().getContentResolver().query(uri, null, null, null, null);
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
        Cursor cursor = bridge.getContext().getContentResolver().query(uri, projection, null, null, null);
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
            retriever.setDataSource(bridge.getContext(), uri);
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
                BitmapFactory.decodeStream(bridge.getContext().getContentResolver().openInputStream(uri), null, options);
                return new FileResolution(options.outHeight, options.outWidth);
            } catch (FileNotFoundException exception) {
                exception.printStackTrace();
                return null;
            }
        } else if (isVideoUri(uri)) {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(bridge.getContext(), uri);
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
