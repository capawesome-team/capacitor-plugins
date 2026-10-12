package io.capawesome.capacitorjs.plugins.tabbar;

import androidx.annotation.NonNull;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "TabBar")
public class TabBarPlugin extends Plugin {

    @PluginMethod
    public void hide(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void selectTabById(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void setColors(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void setTabs(PluginCall call) {
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
