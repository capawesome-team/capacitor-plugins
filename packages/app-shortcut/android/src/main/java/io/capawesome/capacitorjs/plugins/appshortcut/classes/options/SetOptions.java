package io.capawesome.capacitorjs.plugins.appshortcut.classes.options;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.content.pm.ShortcutInfoCompat;
import com.getcapacitor.Bridge;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.appshortcut.AppShortcutHelper;
import io.capawesome.capacitorjs.plugins.appshortcut.AppShortcutPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

public class SetOptions {

    private final List<JSObject> shortcuts;
    private final Context context;
    private final Bridge bridge;

    public SetOptions(@NonNull PluginCall call, @NonNull Context context, @NonNull Bridge bridge) throws Exception {
        JSArray shortcuts = call.getArray("shortcuts");
        if (shortcuts == null) {
            throw new Exception(AppShortcutPlugin.ERROR_SHORTCUTS_MISSING);
        }
        this.shortcuts = shortcuts.toList();
        this.context = context;
        this.bridge = bridge;
    }

    @NonNull
    public List<ShortcutInfoCompat> getShortcuts() throws Exception {
        ArrayList<ShortcutInfoCompat> shortcutsList = new ArrayList<>();
        for (JSONObject shortcut : shortcuts) {
            HashMap<String, String> shortcutMap = AppShortcutHelper.createHashMapFromJSONObject(shortcut);
            String id = shortcutMap.get("id");
            if (id == null) {
                throw new Exception(AppShortcutPlugin.ERROR_ID_MISSING);
            }
            String title = shortcutMap.get("title");
            if (title == null) {
                throw new Exception(AppShortcutPlugin.ERROR_TITLE_MISSING);
            }
            String description = shortcutMap.get("description");

            ShortcutInfoCompat.Builder shortcutInfoCompat = new ShortcutInfoCompat.Builder(context, id);
            shortcutInfoCompat.setShortLabel(title);
            if (description != null) {
                shortcutInfoCompat.setLongLabel(description);
            }
            shortcutsList.add(
                shortcutInfoCompat
                    .setIntent(
                        new Intent(
                            Intent.EXTRA_SHORTCUT_INTENT,
                            bridge.getIntentUri(),
                            bridge.getContext(),
                            bridge.getActivity().getClass()
                        )
                    )
                    .build()
            );
        }
        return shortcutsList;
    }
}
