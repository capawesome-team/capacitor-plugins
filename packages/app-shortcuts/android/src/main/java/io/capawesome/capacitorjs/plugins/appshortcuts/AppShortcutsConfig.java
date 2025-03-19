package io.capawesome.capacitorjs.plugins.appshortcuts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.pm.ShortcutInfoCompat;
import java.util.List;

public class AppShortcutsConfig {

    @Nullable
    private List<ShortcutInfoCompat> shortcuts;

    void setShortcuts(@NonNull List<ShortcutInfoCompat> shortcuts) {
        this.shortcuts = shortcuts;
    }

    @Nullable
    List<ShortcutInfoCompat> getShortcuts() {
        return this.shortcuts;
    }
}
