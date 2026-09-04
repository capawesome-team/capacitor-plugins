package io.capawesome.capacitorjs.plugins.pdfannotator.classes;

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.annotation.RequiresExtension;
import androidx.pdf.ExperimentalPdfApi;
import androidx.pdf.PdfDocument;
import androidx.pdf.PdfWriteHandle;
import androidx.pdf.ink.EditablePdfViewerFragment;

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 18)
@OptIn(markerClass = ExperimentalPdfApi.class)
public class PdfAnnotatorFragment extends EditablePdfViewerFragment {

    public interface Listener {
        void onApplyEditsFailed(@NonNull Throwable throwable);
        void onApplyEditsSuccess(@NonNull PdfWriteHandle handle);
        void onEditModeChanged(boolean editModeEnabled);
        void onLoadDocumentError(@NonNull Throwable throwable);
    }

    @Nullable
    private Listener listener;

    @Override
    public void onApplyEditsFailed(@NonNull Throwable throwable) {
        super.onApplyEditsFailed(throwable);
        if (listener != null) {
            listener.onApplyEditsFailed(throwable);
        }
    }

    @Override
    public void onApplyEditsSuccess(@NonNull PdfWriteHandle handle) {
        super.onApplyEditsSuccess(handle);
        if (listener != null) {
            listener.onApplyEditsSuccess(handle);
        }
    }

    @Override
    public void onEnterEditMode() {
        super.onEnterEditMode();
        if (listener != null) {
            listener.onEditModeChanged(true);
        }
    }

    @Override
    public void onExitEditMode() {
        super.onExitEditMode();
        if (listener != null) {
            listener.onEditModeChanged(false);
        }
    }

    @Override
    public void onLoadDocumentError(@NonNull Throwable throwable) {
        super.onLoadDocumentError(throwable);
        if (listener != null) {
            listener.onLoadDocumentError(throwable);
        }
    }

    @Override
    public void onLoadDocumentSuccess(@NonNull PdfDocument document) {
        super.onLoadDocumentSuccess(document);
        // The toolbox contains the button that enters the edit mode and is hidden by default.
        setToolboxVisible(true);
    }

    public void setListener(@Nullable Listener listener) {
        this.listener = listener;
    }
}
