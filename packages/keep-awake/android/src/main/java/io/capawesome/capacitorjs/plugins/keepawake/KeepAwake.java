package io.capawesome.capacitorjs.plugins.keepawake;

import android.view.WindowManager;
import androidx.annotation.NonNull;
import io.capawesome.capacitorjs.plugins.keepawake.classes.results.IsAvailableResult;
import io.capawesome.capacitorjs.plugins.keepawake.classes.results.IsKeptAwakeResult;

public class KeepAwake {

    @NonNull
    private final KeepAwakePlugin plugin;

    private boolean keptAwake = false;

    public KeepAwake(@NonNull KeepAwakePlugin plugin) {
        this.plugin = plugin;
    }

    public void allowSleep() {
        keptAwake = false;
        plugin
            .getActivity()
            .runOnUiThread(() -> plugin.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON));
    }

    @NonNull
    public IsAvailableResult isAvailable() {
        return new IsAvailableResult(true);
    }

    @NonNull
    public IsKeptAwakeResult isKeptAwake() {
        return new IsKeptAwakeResult(keptAwake);
    }

    public void keepAwake() {
        keptAwake = true;
        plugin.getActivity().runOnUiThread(() -> plugin.getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON));
    }
}
