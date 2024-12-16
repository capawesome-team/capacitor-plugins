package io.capawesome.capacitorjs.plugins.appshortcut;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import io.capawesome.capacitorjs.plugins.appshortcut.classes.results.GetResult;
import io.capawesome.capacitorjs.plugins.appshortcut.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.appshortcut.interfaces.NonEmptyCallback;
import java.util.List;

public class AppShortcut {

    private final Context context;

    public AppShortcut(Context context) {
        this.context = context;
    }

    public void get(@NonNull NonEmptyCallback callback) {
        List<ShortcutInfoCompat> shortcuts = ShortcutManagerCompat.getDynamicShortcuts(context);
        GetResult result = new GetResult(shortcuts);
        callback.success(result);
    }

    public void set(@NonNull List<ShortcutInfoCompat> shortcuts, @NonNull EmptyCallback callback) {
        ShortcutManagerCompat.setDynamicShortcuts(context, shortcuts);
        callback.success();
    }

    public void clear(@NonNull EmptyCallback callback) {
        ShortcutManagerCompat.removeAllDynamicShortcuts(context);
        callback.success();
    }
}
