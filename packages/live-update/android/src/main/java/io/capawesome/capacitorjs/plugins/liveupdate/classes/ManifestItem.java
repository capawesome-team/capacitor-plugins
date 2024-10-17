package io.capawesome.capacitorjs.plugins.liveupdate.classes;

import androidx.annotation.NonNull;
import org.json.JSONObject;

public class ManifestItem {

    @NonNull
    private String checksum;

    @NonNull
    private String href;

    @NonNull
    private int sizeInBytes;

    public ManifestItem(JSONObject json) {
        this.href = json.optString("href");
        this.checksum = json.optString("checksum");
        this.sizeInBytes = json.optInt("sizeInBytes");
    }

    @NonNull
    public String getHref() {
        return href;
    }

    @NonNull
    public String getChecksum() {
        return checksum;
    }

    @NonNull
    public int getSizeInBytes() {
        return sizeInBytes;
    }
}
