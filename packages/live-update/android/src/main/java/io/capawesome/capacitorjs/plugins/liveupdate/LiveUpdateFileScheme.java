package io.capawesome.capacitorjs.plugins.liveupdate;

import androidx.annotation.NonNull;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Helper for the {@code file://} URL scheme path of {@code downloadBundle}.
 * Pure-Java by design so unit tests can run on the JVM without Robolectric
 * or Mockito.
 */
public final class LiveUpdateFileScheme {

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
}
