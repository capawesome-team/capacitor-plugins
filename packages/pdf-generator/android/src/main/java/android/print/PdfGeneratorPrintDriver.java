package android.print;

import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.File;

/**
 * Drives a {@link PrintDocumentAdapter} headlessly to write its content to a PDF file.
 *
 * <p>This class must be declared in the {@code android.print} package because the constructors of
 * {@link PrintDocumentAdapter.LayoutResultCallback} and {@link PrintDocumentAdapter.WriteResultCallback}
 * are package-private and can therefore only be subclassed from within this package.</p>
 */
public class PdfGeneratorPrintDriver {

    public interface Callback {
        void onFailure(@Nullable String message);

        void onSuccess();
    }

    public static void print(
        @NonNull PrintDocumentAdapter adapter,
        @NonNull PrintAttributes attributes,
        @NonNull File file,
        @NonNull Callback callback
    ) {
        adapter.onStart();
        adapter.onLayout(
            null,
            attributes,
            new CancellationSignal(),
            new PrintDocumentAdapter.LayoutResultCallback() {
                @Override
                public void onLayoutFailed(CharSequence error) {
                    adapter.onFinish();
                    callback.onFailure(error == null ? null : error.toString());
                }

                @Override
                public void onLayoutFinished(PrintDocumentInfo info, boolean changed) {
                    write(adapter, file, callback);
                }
            },
            null
        );
    }

    private static void closeQuietly(@NonNull ParcelFileDescriptor descriptor) {
        try {
            descriptor.close();
        } catch (Exception exception) {
            // No operation
        }
    }

    private static void write(@NonNull PrintDocumentAdapter adapter, @NonNull File file, @NonNull Callback callback) {
        ParcelFileDescriptor descriptor;
        try {
            descriptor = ParcelFileDescriptor.open(
                file,
                ParcelFileDescriptor.MODE_CREATE | ParcelFileDescriptor.MODE_TRUNCATE | ParcelFileDescriptor.MODE_READ_WRITE
            );
        } catch (Exception exception) {
            adapter.onFinish();
            callback.onFailure(exception.getMessage());
            return;
        }
        adapter.onWrite(
            new PageRange[] { PageRange.ALL_PAGES },
            descriptor,
            new CancellationSignal(),
            new PrintDocumentAdapter.WriteResultCallback() {
                @Override
                public void onWriteFailed(CharSequence error) {
                    closeQuietly(descriptor);
                    adapter.onFinish();
                    callback.onFailure(error == null ? null : error.toString());
                }

                @Override
                public void onWriteFinished(PageRange[] pages) {
                    closeQuietly(descriptor);
                    adapter.onFinish();
                    callback.onSuccess();
                }
            }
        );
    }
}
