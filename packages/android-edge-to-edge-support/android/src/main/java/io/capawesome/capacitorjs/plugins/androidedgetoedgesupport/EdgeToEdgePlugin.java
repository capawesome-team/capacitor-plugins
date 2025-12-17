package io.capawesome.capacitorjs.plugins.androidedgetoedgesupport;

import android.graphics.Color;
import android.view.ViewGroup;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
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
                try {
                    implementation.enable();
                    call.resolve();
                } catch (Exception exception) {
                    call.reject(exception.getMessage());
                }
            });
    }

    @PluginMethod
    public void disable(PluginCall call) {
        getActivity()
            .runOnUiThread(() -> {
                try {
                    implementation.disable();
                    call.resolve();
                } catch (Exception exception) {
                    call.reject(exception.getMessage());
                }
            });
    }

    @PluginMethod
    public void getInsets(PluginCall call) {
        try {
            ViewGroup.MarginLayoutParams insets = implementation.getInsets();
            JSObject result = new JSObject();
            result.put("bottom", insets.bottomMargin);
            result.put("left", insets.leftMargin);
            result.put("right", insets.rightMargin);
            result.put("top", insets.topMargin);
            call.resolve(result);
        } catch (Exception exception) {
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void setBackgroundColor(PluginCall call) {
        String color = call.getString("color");
        if (color == null) {
            call.reject(ERROR_COLOR_MISSING);
            return;
        }
        getActivity()
            .runOnUiThread(() -> {
                try {
                    implementation.setBackgroundColor(color);
                    call.resolve();
                } catch (Exception exception) {
                    call.reject(exception.getMessage());
                }
            });
    }

    @PluginMethod
    public void setNavigationBarColor(PluginCall call) {
        String color = call.getString("color");
        if (color == null) {
            call.reject(ERROR_COLOR_MISSING);
            return;
        }
        getActivity()
            .runOnUiThread(() -> {
                try {
                    implementation.setNavigationBarColor(color);
                    call.resolve();
                } catch (Exception exception) {
                    call.reject(exception.getMessage());
                }
            });
    }

    @PluginMethod
    public void setStatusBarColor(PluginCall call) {
        String color = call.getString("color");
        if (color == null) {
            call.reject(ERROR_COLOR_MISSING);
            return;
        }
        getActivity()
            .runOnUiThread(() -> {
                try {
                    implementation.setStatusBarColor(color);
                    call.resolve();
                } catch (Exception exception) {
                    call.reject(exception.getMessage());
                }
            });
    }

    private EdgeToEdgeConfig getEdgeToEdgeConfig() {
        EdgeToEdgeConfig config = new EdgeToEdgeConfig();

        try {
            String backgroundColor = getConfig().getString("backgroundColor");
            String statusBarColor = getConfig().getString("statusBarColor");
            String navigationBarColor = getConfig().getString("navigationBarColor");

            // Backward compatibility: if backgroundColor is set but specific colors aren't, use it for both
            if (statusBarColor == null && backgroundColor != null) {
                config.setStatusBarColor(Color.parseColor(backgroundColor));
            } else if (statusBarColor != null) {
                config.setStatusBarColor(Color.parseColor(statusBarColor));
            }

            if (navigationBarColor == null && backgroundColor != null) {
                config.setNavigationBarColor(Color.parseColor(backgroundColor));
            } else if (navigationBarColor != null) {
                config.setNavigationBarColor(Color.parseColor(navigationBarColor));
            }

            // Keep backgroundColor for any legacy code that might use it
            if (backgroundColor != null) {
                config.setBackgroundColor(Color.parseColor(backgroundColor));
            }
        } catch (Exception exception) {
            Logger.error(TAG, "Set config failed.", exception);
        }
        return config;
    }
}
