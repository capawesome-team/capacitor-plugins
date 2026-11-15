package io.capawesome.capacitorjs.plugins.liveupdate.classes;

import androidx.annotation.NonNull;

/**
 * A file of the new manifest whose content is copied from the current bundle.
 */
public class ManifestFileCopy {

    @NonNull
    private final ManifestItem item;

    @NonNull
    private final String sourceHref;

    public ManifestFileCopy(@NonNull String sourceHref, @NonNull ManifestItem item) {
        this.sourceHref = sourceHref;
        this.item = item;
    }

    /**
     * @return The item of the new manifest. Its `href` is the destination of the copy.
     */
    @NonNull
    public ManifestItem getItem() {
        return item;
    }

    /**
     * @return The path in the current bundle that holds the content.
     */
    @NonNull
    public String getSourceHref() {
        return sourceHref;
    }
}
