package io.capawesome.capacitorjs.plugins.liveupdate.classes;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

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
        for (ManifestItem item1 : manifest1.getItems()) {
            for (ManifestItem item2 : manifest2.getItems()) {
                if (item1.getChecksum().equals(item2.getChecksum())) {
                    duplicateItems.add(item1);
                }
            }
        }
        return duplicateItems;
    }

    public static List<ManifestItem> findMissingItems(Manifest manifest1, Manifest manifest2) {
        List<ManifestItem> missingItems = new ArrayList<>();
        for (ManifestItem item1 : manifest1.getItems()) {
            boolean found = false;
            for (ManifestItem item2 : manifest2.getItems()) {
                if (item1.getChecksum().equals(item2.getChecksum())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                missingItems.add(item1);
            }
        }
        return missingItems;
    }
}
