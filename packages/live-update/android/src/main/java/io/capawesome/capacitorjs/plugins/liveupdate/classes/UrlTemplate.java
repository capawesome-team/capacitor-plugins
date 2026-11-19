package io.capawesome.capacitorjs.plugins.liveupdate.classes;

import androidx.annotation.NonNull;

/**
 * The address of a bundle file with a placeholder for the checksum of the file.
 * The placeholder is replaced on the text, never through a URL parser, so no other character changes.
 */
public class UrlTemplate {

    private static final String CHECKSUM_PLACEHOLDER = "{checksum}";

    @NonNull
    private final String value;

    public UrlTemplate(@NonNull String value) {
        this.value = value;
    }

    @NonNull
    public String fill(@NonNull String checksum) {
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
