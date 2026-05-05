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
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Helper for the {@code file://} URL scheme path of {@code downloadBundle}.
 * Pure-Java by design so unit tests can run on the JVM without Robolectric
 * or Mockito.
 */
public final class LiveUpdateFileScheme {

    public interface ProgressListener {
        void onProgress(long downloadedBytes, long totalBytes);
    }

    private LiveUpdateFileScheme() {
        /* utility class */
    }

    /**
     * Parses a {@code file://} URI string and returns the canonical {@link File}
     * after verifying the resolved path lives under one of
     * {@code allowedCanonicalPrefixes}. Each prefix must already be a canonical
     * absolute path; the caller is responsible for canonicalising
     * {@code Context.getFilesDir()}, {@code Context.getCacheDir()}, etc.
     * before passing them in.
     *
     * Rejects non-file schemes and any path that escapes the allowed prefixes
     * via symlink or {@code ..} traversal.
     *
     * @throws IllegalArgumentException for malformed URIs or non-file schemes
     * @throws SecurityException when the resolved path is outside every allowed prefix
     * @throws IOException when canonicalisation fails
     */
    @NonNull
    public static File resolveSandboxedFile(
        @NonNull String sourceFileUri,
        @NonNull String... allowedCanonicalPrefixes
    ) throws IOException {
        URI uri;
        try {
            uri = new URI(sourceFileUri);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid file:// URI");
        }
        String scheme = uri.getScheme();
        if (scheme == null || !"file".equalsIgnoreCase(scheme)) {
            throw new IllegalArgumentException("Invalid file:// URI");
        }
        File candidate;
        try {
            candidate = new File(uri);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid file:// URI");
        }
        File canonical = candidate.getCanonicalFile();
        String canonicalPath = canonical.getAbsolutePath();
        for (String prefix : allowedCanonicalPrefixes) {
            if (canonicalPath.equals(prefix) || canonicalPath.startsWith(prefix + File.separator)) {
                return canonical;
            }
        }
        throw new SecurityException("Sideloaded bundle path is outside the app sandbox");
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
            throw new FileNotFoundException("Sideloaded bundle not found");
        }
        long total = source.length();
        long copied = 0L;
        byte[] buffer = new byte[8192];
        try (
            InputStream in = new FileInputStream(source);
            OutputStream out = new FileOutputStream(destination)
        ) {
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
