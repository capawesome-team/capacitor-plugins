package io.capawesome.capacitorjs.plugins.screenorientation;

import android.content.res.Configuration;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "ScreenOrientation")
public class ScreenOrientationPlugin extends Plugin {

    public static final String ERROR_UNSUPPORTED_ORIENTATION_TYPE = "Unsupported orientation type.";

    public static final String SCREEN_ORIENTATION_CHANGE_EVENT = "screenOrientationChange";

    private ScreenOrientation implementation;

    @Override
    public void load() {
        implementation = new ScreenOrientation(getBridge());
        implementation.setOrientationChangeListener(this::updateOrientation);
    }

    @Override
    public void handleOnConfigurationChanged(Configuration newConfig) {
        super.handleOnConfigurationChanged(newConfig);
        implementation.handleOnConfigurationChanged(newConfig);
    }

    @PluginMethod
    public void lock(PluginCall call) {
        String orientationType = call.getString("type");
        try {
            implementation.lock(orientationType);
            call.resolve();
        } catch (Exception exception) {
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void unlock(PluginCall call) {
        implementation.unlock();
        call.resolve();
    }

    @PluginMethod
    public void getCurrentOrientation(PluginCall call) {
        JSObject ret = new JSObject();
        String orientationType = implementation.getCurrentOrientationTypeKey();
        ret.put("type", orientationType);
        call.resolve(ret);
    }

    private void updateOrientation() {
        JSObject ret = new JSObject();
        String orientationType = implementation.getCurrentOrientationTypeKey();
        ret.put("type", orientationType);
        notifyListeners(SCREEN_ORIENTATION_CHANGE_EVENT, ret);
    }
}
