package io.capawesome.capacitorjs.plugins.liveupdate.classes;

import static io.capawesome.capacitorjs.plugins.liveupdate.classes.JsonFields.getNonEmptyString;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Arrays;
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
     * @return The item, or `null` if `href` or `checksum` is missing or `href` leaves the bundle.
     */
    @Nullable
    public static ManifestItem parse(@NonNull JSONObject json) {
        String href = getNonEmptyString(json, "href");
        if (getNonEmptyString(json, "checksum") == null || href == null || !isHrefInsideBundle(href)) {
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

    private static boolean isHrefInsideBundle(@NonNull String href) {
        return !Arrays.asList(href.split("/", -1)).contains("..");
    }
}
