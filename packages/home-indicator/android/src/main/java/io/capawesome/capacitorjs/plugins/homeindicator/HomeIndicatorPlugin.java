package io.capawesome.capacitorjs.plugins.homeindicator;

import androidx.annotation.NonNull;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "HomeIndicator")
public class HomeIndicatorPlugin extends Plugin {

    @PluginMethod
    public void hide(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void isHidden(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void show(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    private void rejectCallAsUnimplemented(@NonNull PluginCall call) {
        call.unimplemented("This method is not available on this platform.");
    }
}
