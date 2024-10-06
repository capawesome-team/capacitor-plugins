package io.capawesome.capacitorjs.plugins.liveupdate.classes.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

public class ManifestItem {
    @NonNull
    private String id;
    @NonNull
    private String href;
    @NonNull
    private String checksum;
    @Nullable
    private String signature;
    @NonNull
    private String sizeInBytes;

    public ManifestItem(JSONObject json) {
        this.id = json.optString("id");
        this.href = json.optString("href");
        this.checksum = json.optString("checksum");
        // TODO: "null"?
        this.signature = json.optString("signature", null);
        this.sizeInBytes = json.optString("sizeInBytes");
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getHref() {
        return href;
    }

    @NonNull
    public String getChecksum() {
        return checksum;
    }

    @Nullable
    public String getSignature() {
        return signature;
    }

    @NonNull
    public String getSizeInBytes() {
        return sizeInBytes;
    }
}
