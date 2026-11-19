package io.capawesome.capacitorjs.plugins.liveupdate.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Manifest {

    @NonNull
    private final List<ManifestItem> items;

    private Manifest(@NonNull List<ManifestItem> items) {
        this.items = items;
    }

    /**
     * @return The manifest, or `null` if it is not a JSON list or an item has no `href` or no `checksum`.
     */
    @Nullable
    public static Manifest parse(@NonNull String text) {
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(text);
        } catch (JSONException exception) {
            return null;
        }
        List<ManifestItem> items = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject itemJson = jsonArray.optJSONObject(i);
            ManifestItem item = itemJson == null ? null : ManifestItem.parse(itemJson);
            if (item == null) {
                return null;
            }
            items.add(item);
        }
        return new Manifest(items);
    }

    /**
     * A file whose checksum is in the current manifest is copied from the first path that holds this content there.
     * Every other file is downloaded. Without a current manifest, every file is downloaded.
     */
    @NonNull
    public ManifestPlan createPlan(@Nullable Manifest currentManifest) {
        Map<String, String> currentHrefsByChecksum = currentManifest == null ? new HashMap<>() : currentManifest.mapFirstHrefsByChecksum();
        List<ManifestFileCopy> filesToCopy = new ArrayList<>();
        List<ManifestItem> itemsToDownload = new ArrayList<>();
        for (ManifestItem item : items) {
            String sourceHref = currentHrefsByChecksum.get(item.getChecksum());
            if (sourceHref == null) {
                itemsToDownload.add(item);
            } else {
                filesToCopy.add(new ManifestFileCopy(sourceHref, item));
            }
        }
        return new ManifestPlan(filesToCopy, itemsToDownload);
    }

    @NonNull
    public List<ManifestItem> getItems() {
        return items;
    }

    @NonNull
    private Map<String, String> mapFirstHrefsByChecksum() {
        Map<String, String> hrefsByChecksum = new HashMap<>();
        for (ManifestItem item : items) {
            hrefsByChecksum.putIfAbsent(item.getChecksum(), item.getHref());
        }
        return hrefsByChecksum;
    }
}
