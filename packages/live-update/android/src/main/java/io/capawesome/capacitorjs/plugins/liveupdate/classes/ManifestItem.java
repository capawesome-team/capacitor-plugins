package io.capawesome.capacitorjs.plugins.liveupdate.classes;

import static io.capawesome.capacitorjs.plugins.liveupdate.classes.JsonFields.getNonEmptyString;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.json.JSONObject;

public class ManifestItem {

    @NonNull
    private final String checksum;

    @NonNull
    private final String href;

    private final int sizeInBytes;

    private ManifestItem(@NonNull JSONObject json) {
        this.checksum = getNonEmptyString(json, "checksum");
        this.href = getNonEmptyString(json, "href");
        this.sizeInBytes = json.optInt("sizeInBytes", 0);
    }

    /**
     * @return The item, or `null` if `href` or `checksum` is missing.
     */
    @Nullable
    public static ManifestItem parse(@NonNull JSONObject json) {
        if (getNonEmptyString(json, "checksum") == null || getNonEmptyString(json, "href") == null) {
            return null;
        }
        return new ManifestItem(json);
    }

    @NonNull
    public String getChecksum() {
        return checksum;
    }

    @NonNull
    public String getHref() {
        return href;
    }

    public int getSizeInBytes() {
        return sizeInBytes;
    }
}
