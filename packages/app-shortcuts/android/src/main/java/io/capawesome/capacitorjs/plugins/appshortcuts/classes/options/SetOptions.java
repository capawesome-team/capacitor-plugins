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
        this.shortcuts = AppShortcutsHelper.createShortcutInfoCompatList(shortcuts, context, bridge);
    }

    @NonNull
    public List<ShortcutInfoCompat> getShortcuts() {
        return shortcuts;
    }
}
