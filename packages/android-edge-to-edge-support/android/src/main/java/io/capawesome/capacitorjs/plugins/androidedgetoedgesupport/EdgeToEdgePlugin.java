package io.capawesome.capacitorjs.plugins.androidedgetoedgesupport;

import android.graphics.Color;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "EdgeToEdge")
public class EdgeToEdgePlugin extends Plugin {

    private static final String ERROR_COLOR_MISSING = "color must be provided.";
    private static final String TAG = "EdgeToEdge";

    private EdgeToEdge implementation;

    @Override
    public void load() {
        EdgeToEdgeConfig config = getEdgeToEdgeConfig();
        implementation = new EdgeToEdge(this, config);
    }

    @PluginMethod
    public void enable(PluginCall call) {
        getActivity()
            .runOnUiThread(() -> {
                implementation.enable();
                call.resolve();
            });
    }

    @PluginMethod
    public void disable(PluginCall call) {
        getActivity()
            .runOnUiThread(() -> {
                implementation.disable();
                call.resolve();
            });
    }

    @PluginMethod
    public void getInsets(PluginCall call) {
        ViewGroup.MarginLayoutParams insets = implementation.getInsets();
        JSObject result = new JSObject();
        result.put("bottom", insets.bottomMargin);
        result.put("left", insets.leftMargin);
        result.put("right", insets.rightMargin);
        result.put("top", insets.topMargin);
        call.resolve(result);
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

    private EdgeToEdgeConfig getEdgeToEdgeConfig() {
        EdgeToEdgeConfig config = new EdgeToEdgeConfig();

        String backgroundColor = getConfig().getString("backgroundColor");
        if (backgroundColor != null) {
            config.setBackgroundColor(Color.parseColor(backgroundColor));
        }
        return config;
    }
}
