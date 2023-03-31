package io.capawesome.capacitorjs.plugins.backgroundtask;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "BackgroundTask")
public class BackgroundTaskPlugin extends Plugin {

    private BackgroundTask implementation = new BackgroundTask();

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void beforeExit(PluginCall call) {
        // No-op for now. Android support will be added in a later version.
        String callbackId = call.getCallbackId();
        JSObject ret = new JSObject();
        ret.put("taskId", callbackId);
        call.resolve(ret);
    }

    @PluginMethod
    public void finish(PluginCall call) {
        // No-op for now. Android support will be added in a later version.
        call.resolve();
    }
}
