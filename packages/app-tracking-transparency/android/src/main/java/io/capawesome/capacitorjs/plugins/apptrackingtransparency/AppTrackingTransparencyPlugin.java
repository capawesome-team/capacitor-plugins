package io.capawesome.capacitorjs.plugins.apptrackingtransparency;

import androidx.annotation.NonNull;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "AppTrackingTransparency")
public class AppTrackingTransparencyPlugin extends Plugin {

    @PluginMethod
    public void getAdvertisingIdentifier(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void getStatus(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void requestPermission(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    private void rejectCallAsUnimplemented(@NonNull PluginCall call) {
        call.unimplemented("This method is not available on this platform.");
    }
}
