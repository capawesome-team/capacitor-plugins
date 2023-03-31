package io.capawesome.capacitorjs.plugins.badge;

import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;

@CapacitorPlugin(name = "Badge", permissions = @Permission(strings = {}, alias = "display"))
public class BadgePlugin extends Plugin {

    private Badge implementation;

    @Override
    public void load() {
        BadgeConfig config = getBadgeConfig();
        implementation = new Badge(getContext(), config);
    }

    @Override
    public void handleOnResume() {
        super.handleOnResume();
        implementation.handleOnResume();
    }

    @PluginMethod
    public void get(PluginCall call) {
        try {
            int count = implementation.get();
            JSObject ret = new JSObject();
            ret.put("count", count);
            call.resolve(ret);
        } catch (Exception ex) {
            call.reject(ex.getLocalizedMessage());
        }
    }

    @PluginMethod
    public void set(PluginCall call) {
        try {
            int count = call.getInt("count", 0);
            implementation.set(count);
            call.resolve();
        } catch (Exception ex) {
            call.reject(ex.getLocalizedMessage());
        }
    }

    @PluginMethod
    public void increase(PluginCall call) {
        try {
            implementation.increase();
            call.resolve();
        } catch (Exception ex) {
            call.reject(ex.getLocalizedMessage());
        }
    }

    @PluginMethod
    public void decrease(PluginCall call) {
        try {
            implementation.decrease();
            call.resolve();
        } catch (Exception ex) {
            call.reject(ex.getLocalizedMessage());
        }
    }

    @PluginMethod
    public void clear(PluginCall call) {
        try {
            implementation.clear();
            call.resolve();
        } catch (Exception ex) {
            call.reject(ex.getLocalizedMessage());
        }
    }

    @PluginMethod
    public void isSupported(PluginCall call) {
        try {
            boolean isSupported = implementation.isSupported();
            JSObject ret = new JSObject();
            ret.put("isSupported", isSupported);
            call.resolve(ret);
        } catch (Exception ex) {
            call.reject(ex.getLocalizedMessage());
        }
    }

    private BadgeConfig getBadgeConfig() {
        BadgeConfig config = new BadgeConfig();

        Boolean persist = getConfig().getBoolean("persist", config.getPersist());
        config.setPersist(persist);
        Boolean autoClear = getConfig().getBoolean("autoClear", config.getAutoClear());
        config.setAutoClear(autoClear);

        return config;
    }
}
