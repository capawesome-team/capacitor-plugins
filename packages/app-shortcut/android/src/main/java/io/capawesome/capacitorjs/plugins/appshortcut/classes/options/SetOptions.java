package io.capawesome.capacitorjs.plugins.appshortcut.classes.options;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.pm.ShortcutInfoCompat;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.appshortcut.AppShortcutPlugin;
import java.util.ArrayList;
import java.util.List;

public class SetOptions {

    private final List<JSObject> shortcuts;
    private final Context context;

    public SetOptions(@NonNull PluginCall call, @NonNull Context context) throws Exception {
        JSArray shortcuts = call.getArray("shortcuts");
        if (shortcuts == null) {
            throw new Exception(AppShortcutPlugin.ERROR_SHORTCUTS_MISSING);
        }
        this.shortcuts = shortcuts.toList();
        this.context = context;
    }

    @NonNull
    public List<ShortcutInfoCompat> getShortcuts() throws Exception {
        ArrayList<ShortcutInfoCompat> shortcutsList = new ArrayList<>();
        for (JSObject shortcut : shortcuts) {
            String id = shortcut.getString("id");
            if (id == null) {
                throw new Exception(AppShortcutPlugin.ERROR_ID_MISSING);
            }
            String title = shortcut.getString("title");
            if (title == null) {
                throw new Exception(AppShortcutPlugin.ERROR_TITLE_MISSING);
            }
            String description = shortcut.getString("description");

            ShortcutInfoCompat.Builder shortcutInfoCompat = new ShortcutInfoCompat.Builder(context, id);
            shortcutInfoCompat.setShortLabel(title);
            if (description != null) {
                shortcutInfoCompat.setLongLabel(description);
            }
            shortcutsList.add(shortcutInfoCompat.build());
        }
        return shortcutsList;
    }
}
