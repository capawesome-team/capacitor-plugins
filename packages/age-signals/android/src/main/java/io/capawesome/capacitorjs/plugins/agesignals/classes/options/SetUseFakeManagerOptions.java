package io.capawesome.capacitorjs.plugins.agesignals.classes.options;

import com.getcapacitor.PluginCall;

public class SetUseFakeManagerOptions {

    private final boolean useFake;

    public SetUseFakeManagerOptions(PluginCall call) {
        this.useFake = call.getBoolean("useFake", false);
    }

    public boolean getUseFake() {
        return useFake;
    }
}
