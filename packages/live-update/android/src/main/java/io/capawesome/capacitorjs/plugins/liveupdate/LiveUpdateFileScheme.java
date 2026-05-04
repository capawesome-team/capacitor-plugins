package io.capawesome.capacitorjs.plugins.liveupdate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Helper for the {@code file://} URL scheme path of {@code downloadBundle}.
 * Pure-Java by design so unit tests can run on the JVM without Robolectric.
 */
public final class LiveUpdateFileScheme {

    public interface ProgressListener {
        void onProgress(long downloadedBytes, long totalBytes);
    }

    private LiveUpdateFileScheme() {
        /* utility class */
    }

    /**
     * Copies {@code source} to {@code destination}, reporting incremental
     * progress to {@code progress} (may be {@code null}). The total byte count
     * reported is {@code source.length()} captured before copying begins.
     *
     * @return the number of bytes actually copied (equal to source size on success)
     * @throws FileNotFoundException if {@code source} does not exist
     * @throws IOException on read/write failure
     */
    public static long copyAndReportProgress(
        @NonNull File source,
        @NonNull File destination,
        @Nullable ProgressListener progress
    ) throws IOException {
        if (!source.exists()) {
            throw new FileNotFoundException("Sideloaded bundle not found at " + source.getAbsolutePath());
        }
        long total = source.length();
        long copied = 0L;
        byte[] buffer = new byte[8192];
        try (InputStream in = new FileInputStream(source); OutputStream out = new FileOutputStream(destination)) {
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
                copied += read;
                if (progress != null) {
                    progress.onProgress(copied, total);
                }
            }
        }
        return copied;
    }
}
