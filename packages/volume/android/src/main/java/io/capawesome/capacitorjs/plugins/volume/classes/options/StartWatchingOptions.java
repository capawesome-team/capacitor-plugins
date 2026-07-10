package io.capawesome.capacitorjs.plugins.volume.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;

public class StartWatchingOptions {

    private final boolean suppressVolumeChange;

    public StartWatchingOptions(@NonNull PluginCall call) {
        this.suppressVolumeChange = call.getBoolean("suppressVolumeChange", false);
    }

    public boolean isSuppressVolumeChange() {
        return suppressVolumeChange;
    }
}
