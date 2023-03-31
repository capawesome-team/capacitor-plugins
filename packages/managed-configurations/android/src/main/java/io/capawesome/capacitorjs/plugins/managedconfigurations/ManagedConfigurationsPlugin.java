package io.capawesome.capacitorjs.plugins.managedconfigurations;

import android.content.res.Configuration;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import org.json.JSONObject;

@CapacitorPlugin(name = "ManagedConfigurations")
public class ManagedConfigurationsPlugin extends Plugin {

    public static final String ERROR_KEY_MISSING = "key must be provided.";

    private ManagedConfigurations implementation;

    @Override
    public void load() {
        implementation = new ManagedConfigurations(getBridge());
    }

    @Override
    public void handleOnConfigurationChanged(Configuration newConfig) {
        super.handleOnConfigurationChanged(newConfig);
        implementation.refreshApplicationRestrictions();
    }

    @PluginMethod
    public void getString(PluginCall call) {
        String key = call.getString("key");
        if (key == null) {
            call.reject(ERROR_KEY_MISSING);
            return;
        }

        String value = implementation.getString(key);

        JSObject ret = new JSObject();
        ret.put("value", value == null ? JSONObject.NULL : value);
        call.resolve(ret);
    }

    @PluginMethod
    public void getNumber(PluginCall call) {
        String key = call.getString("key");
        if (key == null) {
            call.reject(ERROR_KEY_MISSING);
            return;
        }

        Integer value = implementation.getInteger(key);

        JSObject ret = new JSObject();
        ret.put("value", value == null ? JSONObject.NULL : value);
        call.resolve(ret);
    }

    @PluginMethod
    public void getBoolean(PluginCall call) {
        String key = call.getString("key");
        if (key == null) {
            call.reject(ERROR_KEY_MISSING);
            return;
        }

        Boolean value = implementation.getBoolean(key);

        JSObject ret = new JSObject();
        ret.put("value", value == null ? JSONObject.NULL : value);
        call.resolve(ret);
    }
}
