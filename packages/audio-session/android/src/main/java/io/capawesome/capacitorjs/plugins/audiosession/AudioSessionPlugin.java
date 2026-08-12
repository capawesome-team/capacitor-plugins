package io.capawesome.capacitorjs.plugins.audiosession;

import androidx.annotation.NonNull;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "AudioSession")
public class AudioSessionPlugin extends Plugin {

    @PluginMethod
    public void configure(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void getCurrentOutputs(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void overrideOutput(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void setActive(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    private void rejectCallAsUnimplemented(@NonNull PluginCall call) {
        call.unimplemented("This method is not available on this platform.");
    }
}
