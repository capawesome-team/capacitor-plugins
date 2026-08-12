package io.capawesome.capacitorjs.plugins.clipboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import io.capawesome.capacitorjs.plugins.clipboard.classes.ClipboardContentType;
import io.capawesome.capacitorjs.plugins.clipboard.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.clipboard.classes.options.WriteOptions;
import io.capawesome.capacitorjs.plugins.clipboard.classes.results.ReadResult;
import io.capawesome.capacitorjs.plugins.clipboard.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.clipboard.interfaces.NonEmptyResultCallback;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Clipboard {

    private static final String DEFAULT_LABEL = "";
    private static final String IMAGE_FILE_NAME = "capawesome_capacitor_clipboard_image.png";

    @NonNull
    private final ClipboardPlugin plugin;

    public Clipboard(@NonNull ClipboardPlugin plugin) {
        this.plugin = plugin;
    }

    public void read(@NonNull NonEmptyResultCallback<ReadResult> callback) {
        try {
            ClipboardManager clipboardManager = getClipboardManager();
            ClipData clipData = clipboardManager.getPrimaryClip();
            if (clipData == null || clipData.getItemCount() == 0) {
                callback.error(CustomExceptions.EMPTY_CLIPBOARD);
                return;
            }
            ClipData.Item item = clipData.getItemAt(0);
            ReadResult result = createReadResult(item);
            if (result == null) {
                callback.error(CustomExceptions.EMPTY_CLIPBOARD);
                return;
            }
            callback.success(result);
        } catch (Exception exception) {
            callback.error(CustomExceptions.READ_FAILED);
        }
    }

    public void write(@NonNull WriteOptions options, @NonNull EmptyCallback callback) {
        try {
            String label = options.getLabel() == null ? DEFAULT_LABEL : options.getLabel();
            ClipData clipData;
            if (options.getImage() != null) {
                clipData = createImageClipData(label, options.getImage());
            } else if (options.getHtml() != null) {
                String text = options.getText() == null ? options.getHtml() : options.getText();
                clipData = ClipData.newHtmlText(label, text, options.getHtml());
            } else if (options.getUrl() != null) {
                clipData = ClipData.newRawUri(label, Uri.parse(options.getUrl()));
            } else {
                clipData = ClipData.newPlainText(label, options.getText());
            }
            getClipboardManager().setPrimaryClip(clipData);
            callback.success();
        } catch (Exception exception) {
            callback.error(CustomExceptions.WRITE_FAILED);
        }
    }

    @NonNull
    private ClipData createImageClipData(@NonNull String label, @NonNull String image) throws Exception {
        byte[] bytes = decodeDataUrl(image);
        File file = new File(getContext().getCacheDir(), IMAGE_FILE_NAME);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(bytes);
        }
        String authority = getContext().getPackageName() + ".fileprovider";
        Uri uri = FileProvider.getUriForFile(getContext(), authority, file);
        return ClipData.newUri(getContext().getContentResolver(), label, uri);
    }

    @Nullable
    private ReadResult createReadResult(@NonNull ClipData.Item item) throws Exception {
        Uri uri = item.getUri();
        if (uri != null) {
            String mimeType = getContext().getContentResolver().getType(uri);
            if (mimeType != null && mimeType.startsWith("image/")) {
                return new ReadResult(ClipboardContentType.IMAGE, encodeImageAsDataUrl(uri));
            }
            return new ReadResult(ClipboardContentType.URL, uri.toString());
        }
        String html = item.getHtmlText();
        if (html != null) {
            return new ReadResult(ClipboardContentType.HTML, html);
        }
        CharSequence text = item.getText();
        if (text != null) {
            String value = text.toString();
            if (value.startsWith("http://") || value.startsWith("https://")) {
                return new ReadResult(ClipboardContentType.URL, value);
            }
            return new ReadResult(ClipboardContentType.TEXT, value);
        }
        return null;
    }

    @NonNull
    private byte[] decodeDataUrl(@NonNull String dataUrl) throws Exception {
        int index = dataUrl.indexOf(',');
        String base64 = index == -1 ? dataUrl : dataUrl.substring(index + 1);
        return Base64.decode(base64, Base64.DEFAULT);
    }

    @NonNull
    private String encodeImageAsDataUrl(@NonNull Uri uri) throws Exception {
        try (InputStream inputStream = getContext().getContentResolver().openInputStream(uri)) {
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            String base64 = Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP);
            return "data:image/png;base64," + base64;
        }
    }

    @NonNull
    private ClipboardManager getClipboardManager() {
        return (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @NonNull
    private Context getContext() {
        return plugin.getContext();
    }
}
