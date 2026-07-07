package io.capawesome.capacitorjs.plugins.textinteraction;

import androidx.annotation.NonNull;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "TextInteraction")
public class TextInteractionPlugin extends Plugin {

    @PluginMethod
    public void disable(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void enable(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void isEnabled(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    private void rejectCallAsUnimplemented(@NonNull PluginCall call) {
        call.unimplemented("This method is not available on this platform.");
    }
}
