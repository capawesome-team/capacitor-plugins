package io.capawesome.capacitorjs.plugins.appshortcuts.classes.options;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.graphics.drawable.IconCompat;
import com.getcapacitor.Bridge;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.appshortcuts.AppShortcutsHelper;
import io.capawesome.capacitorjs.plugins.appshortcuts.AppShortcutsPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

public class SetOptions {

    private final List<ShortcutInfoCompat> shortcuts;

    public SetOptions(@NonNull PluginCall call, @NonNull Context context, @NonNull Bridge bridge) throws Exception {
        JSArray shortcuts = call.getArray("shortcuts");
        if (shortcuts == null) {
            throw new Exception(AppShortcutsPlugin.ERROR_SHORTCUTS_MISSING);
        }
        this.shortcuts = this.createShortcutInfoCompatList(shortcuts, context, bridge);
    }

    @NonNull
    public List<ShortcutInfoCompat> getShortcuts() {
        return shortcuts;
    }

    private List<ShortcutInfoCompat> createShortcutInfoCompatList(JSArray shortcuts, Context context, Bridge bridge) throws Exception {
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
