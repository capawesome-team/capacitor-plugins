package io.capawesome.capacitorjs.plugins.appshortcuts;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.graphics.drawable.IconCompat;
import com.getcapacitor.Bridge;
import com.getcapacitor.JSArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class AppShortcutsHelper {

    @NonNull
    public static HashMap<String, Object> createHashMapFromJSONObject(@NonNull JSONObject object) throws JSONException {
        HashMap<String, Object> map = new HashMap<>();
        Iterator<String> keys = object.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            map.put(key, object.get(key));
        }
        return map;
    }

    @NonNull
    public static List<ShortcutInfoCompat> createShortcutInfoCompatList(JSArray shortcuts, Context context, Bridge bridge)
        throws Exception {
        ArrayList<ShortcutInfoCompat> shortcutInfoCompatList = new ArrayList<>();
        List<JSONObject> shortcutsList = shortcuts.toList();
        for (JSONObject shortcut : shortcutsList) {
            HashMap<String, Object> shortcutMap = AppShortcutsHelper.createHashMapFromJSONObject(shortcut);
            Object id = shortcutMap.get("id");
            if (id == null) {
                throw new Exception(AppShortcutsPlugin.ERROR_ID_MISSING);
            }
            Object title = shortcutMap.get("title");
            if (title == null) {
                throw new Exception(AppShortcutsPlugin.ERROR_TITLE_MISSING);
            }
            String description = (String) shortcutMap.get("description");
            Object icon = shortcutMap.get("icon");

            ShortcutInfoCompat.Builder shortcutInfoCompat = new ShortcutInfoCompat.Builder(context, (String) id);
            shortcutInfoCompat.setShortLabel((String) title);
            if (description != null) {
                shortcutInfoCompat.setLongLabel(description);
            }
            shortcutInfoCompat.setIntent(
                new Intent(Intent.ACTION_VIEW, bridge.getIntentUri(), bridge.getContext(), bridge.getActivity().getClass()).putExtra(
                    AppShortcutsPlugin.INTENT_EXTRA_ITEM_NAME,
                    (String) id
                )
            );
            if (icon != null) {
                shortcutInfoCompat.setIcon(IconCompat.createWithResource(context, (int) icon));
            }

            shortcutInfoCompatList.add(shortcutInfoCompat.build());
        }
        return shortcutInfoCompatList;
    }
}
