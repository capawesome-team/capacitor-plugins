package io.capawesome.capacitorjs.plugins.androidedgetoedge;

import androidx.annotation.Nullable;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "EdgeToEdge")
public class EdgeToEdgePlugin extends Plugin {

    private static final String ERROR_COLOR_MISSING = "color must be provided.";
    private static final String TAG = "EdgeToEdge";

    @Nullable
    private EdgeToEdge implementation;

    @Override
    public void load() {
        implementation = new EdgeToEdge(this);
    }

    @PluginMethod
    public void setBackgroundColor(PluginCall call) {
        String color = call.getString("color");
        if (color == null) {
            call.reject(ERROR_COLOR_MISSING);
            return;
        }
        implementation.setBackgroundColor(color);
        call.resolve();
    }
}
