package io.capawesome.capacitorjs.plugins.liveupdate.classes;

import androidx.annotation.NonNull;

/**
 * The address of a bundle file with the placeholder `{checksum}` for the checksum of the file.
 */
public class UrlTemplate {

    private static final String CHECKSUM_PLACEHOLDER = "{checksum}";

    @NonNull
    private final String value;

    public UrlTemplate(@NonNull String value) {
        this.value = value;
    }

    /**
     * Replaces the placeholder on the text, never through a URL parser, so that no other character of the template changes.
     */
    @NonNull
    public String buildFileUrl(@NonNull String checksum) {
        return value.replace(CHECKSUM_PLACEHOLDER, checksum);
    }

    @NonNull
    public String getValue() {
        return value;
    }

    public boolean hasChecksumPlaceholder() {
        return value.contains(CHECKSUM_PLACEHOLDER);
    }
}
