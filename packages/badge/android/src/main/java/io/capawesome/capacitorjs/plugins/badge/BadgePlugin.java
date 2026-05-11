package io.capawesome.capacitorjs.plugins.badge;

import android.Manifest;
import android.os.Build;
import com.getcapacitor.JSObject;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;

@CapacitorPlugin(
    name = "Badge",
    permissions = @Permission(strings = { Manifest.permission.POST_NOTIFICATIONS }, alias = BadgePlugin.PERMISSION_DISPLAY)
)
public class BadgePlugin extends Plugin {

    public static final String PERMISSION_DISPLAY = "display";

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

    @Override
    @PluginMethod
    public void checkPermissions(PluginCall call) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            call.resolve(createPermissionsResult("granted"));
        } else {
            super.checkPermissions(call);
        }
    }

    @Override
    @PluginMethod
    public void requestPermissions(PluginCall call) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            call.resolve(createPermissionsResult("granted"));
        } else if (getPermissionState(PERMISSION_DISPLAY) == PermissionState.GRANTED) {
            this.checkPermissions(call);
        } else {
            requestPermissionForAlias(PERMISSION_DISPLAY, call, "permissionsCallback");
        }
    }

    @PermissionCallback
    private void permissionsCallback(PluginCall call) {
        this.checkPermissions(call);
    }

    private BadgeConfig getBadgeConfig() {
        BadgeConfig config = new BadgeConfig();

        Boolean persist = getConfig().getBoolean("persist", config.getPersist());
        config.setPersist(persist);
        Boolean autoClear = getConfig().getBoolean("autoClear", config.getAutoClear());
        config.setAutoClear(autoClear);

        return config;
    }

    private JSObject createPermissionsResult(String display) {
        JSObject result = new JSObject();
        result.put(PERMISSION_DISPLAY, display);
        return result;
    }
}
