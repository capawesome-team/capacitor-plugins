package io.capawesome.capacitorjs.plugins.pdfviewer;

import android.content.ContentResolver;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.shockwave.pdfium.PdfPasswordException;
import io.capawesome.capacitorjs.plugins.pdfviewer.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.pdfviewer.classes.PdfViewerDialog;
import io.capawesome.capacitorjs.plugins.pdfviewer.classes.events.PageChangeEvent;
import io.capawesome.capacitorjs.plugins.pdfviewer.classes.options.OpenOptions;
import io.capawesome.capacitorjs.plugins.pdfviewer.interfaces.EmptyCallback;
import java.io.File;

public class PdfViewer {

    @Nullable
    private PdfViewerDialog dialog;

    @NonNull
    private final PdfViewerPlugin plugin;

    public PdfViewer(@NonNull PdfViewerPlugin plugin) {
        this.plugin = plugin;
    }

    public void close(@NonNull EmptyCallback callback) {
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                PdfViewerDialog dialog = this.dialog;
                if (dialog != null) {
                    dialog.dismiss();
                }
                callback.success();
            });
    }

    public void open(@NonNull OpenOptions options, @NonNull EmptyCallback callback) {
        File file = getFileByPath(options.getPath());
        if (file == null || !file.exists()) {
            callback.error(CustomExceptions.FILE_NOT_FOUND);
            return;
        }
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                PdfViewerDialog previousDialog = this.dialog;
                if (previousDialog != null) {
                    previousDialog.dismiss();
                }
                PdfViewerDialog dialog = new PdfViewerDialog(
                    plugin.getActivity(),
                    file,
                    options,
                    new PdfViewerDialog.Listener() {
                        @Override
                        public void onClosed(@NonNull PdfViewerDialog dialog) {
                            handleClosed(dialog);
                        }

                        @Override
                        public void onLoadError(@NonNull PdfViewerDialog dialog, @NonNull Throwable throwable) {
                            handleLoadError(dialog);
                            callback.error(getExceptionForThrowable(throwable, options.getPassword() != null));
                        }

                        @Override
                        public void onLoaded() {
                            callback.success();
                        }

                        @Override
                        public void onPageChanged(int page) {
                            plugin.notifyPageChangeListeners(new PageChangeEvent(page));
                        }
                    }
                );
                this.dialog = dialog;
                dialog.show();
            });
    }

    @NonNull
    private Exception getExceptionForThrowable(@NonNull Throwable throwable, boolean passwordProvided) {
        if (throwable instanceof PdfPasswordException) {
            return passwordProvided ? CustomExceptions.PASSWORD_INVALID : CustomExceptions.PASSWORD_REQUIRED;
        }
        return CustomExceptions.LOAD_FAILED;
    }

    @Nullable
    private File getFileByPath(@NonNull String path) {
        Uri uri = Uri.parse(path);
        String scheme = uri.getScheme();
        if (scheme != null && !scheme.equals(ContentResolver.SCHEME_FILE)) {
            return null;
        }
        String filePath = scheme == null ? path : uri.getPath();
        if (filePath == null) {
            return null;
        }
        return new File(filePath);
    }

    private void handleClosed(@NonNull PdfViewerDialog dialog) {
        if (this.dialog == dialog) {
            this.dialog = null;
        }
        plugin.notifyClosedListeners();
    }

    private void handleLoadError(@NonNull PdfViewerDialog dialog) {
        if (this.dialog == dialog) {
            this.dialog = null;
        }
    }
}
