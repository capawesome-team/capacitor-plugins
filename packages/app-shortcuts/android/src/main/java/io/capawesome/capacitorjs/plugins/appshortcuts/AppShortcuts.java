package io.capawesome.capacitorjs.plugins.appshortcuts;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import io.capawesome.capacitorjs.plugins.appshortcuts.classes.options.SetOptions;
import io.capawesome.capacitorjs.plugins.appshortcuts.classes.results.GetResult;
import io.capawesome.capacitorjs.plugins.appshortcuts.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.appshortcuts.interfaces.NonEmptyCallback;
import io.capawesome.capacitorjs.plugins.appshortcuts.interfaces.Result;
import java.util.List;

public class AppShortcuts {

    private final Context context;

    public AppShortcuts(Context context) {
        this.context = context;
    }

    public void get(@NonNull NonEmptyCallback<Result> callback) {
        List<ShortcutInfoCompat> shortcuts = ShortcutManagerCompat.getDynamicShortcuts(context);
        GetResult result = new GetResult(shortcuts);
        callback.success(result);
    }

    public void set(@NonNull SetOptions options, @NonNull EmptyCallback callback) {
        List<ShortcutInfoCompat> shortcuts = options.getShortcuts();

        ShortcutManagerCompat.setDynamicShortcuts(context, shortcuts);
        callback.success();
    }

    public void clear(@NonNull EmptyCallback callback) {
        ShortcutManagerCompat.removeAllDynamicShortcuts(context);
        callback.success();
    }
}
