package io.capawesome.capacitorjs.plugins.liveupdate.classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;

public class Manifest {

    private List<ManifestItem> items = new ArrayList<>();

    public Manifest(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            ManifestItem manifestItem = new ManifestItem(jsonArray.getJSONObject(i));
            items.add(manifestItem);
        }
    }

    public List<ManifestItem> getItems() {
        return items;
    }

    public static List<ManifestItem> findDuplicateItems(Manifest manifest1, Manifest manifest2) {
        List<ManifestItem> duplicateItems = new ArrayList<>();
        Set<String> checksums = new HashSet<>();

        for (ManifestItem item : manifest2.getItems()) {
            checksums.add(item.getChecksum());
        }

        for (ManifestItem item : manifest1.getItems()) {
            if (checksums.contains(item.getChecksum())) {
                duplicateItems.add(item);
            }
        }

        return duplicateItems;
    }

    public static List<ManifestItem> findMissingItems(Manifest manifest1, Manifest manifest2) {
        List<ManifestItem> missingItems = new ArrayList<>();
        Set<String> checksums = new HashSet<>();

        for (ManifestItem item : manifest2.getItems()) {
            checksums.add(item.getChecksum());
        }

        for (ManifestItem item : manifest1.getItems()) {
            if (!checksums.contains(item.getChecksum())) {
                missingItems.add(item);
            }
        }

        return missingItems;
    }
}
