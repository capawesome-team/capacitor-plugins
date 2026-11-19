package io.capawesome.capacitorjs.plugins.liveupdate.classes;

import androidx.annotation.NonNull;
import java.util.List;

/**
 * The files of a new manifest, split into the files to copy from the current bundle and the files to download.
 * Both lists keep the order of the new manifest.
 */
public class ManifestPlan {

    @NonNull
    private final List<ManifestFileCopy> filesToCopy;

    @NonNull
    private final List<ManifestItem> itemsToDownload;

    public ManifestPlan(@NonNull List<ManifestFileCopy> filesToCopy, @NonNull List<ManifestItem> itemsToDownload) {
        this.filesToCopy = filesToCopy;
        this.itemsToDownload = itemsToDownload;
    }

    @NonNull
    public List<ManifestFileCopy> getFilesToCopy() {
        return filesToCopy;
    }

    @NonNull
    public List<ManifestItem> getItemsToDownload() {
        return itemsToDownload;
    }
}
