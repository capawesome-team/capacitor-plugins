package io.capawesome.capacitorjs.plugins.nodejs.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StartOptions {

    @NonNull
    private final List<String> args;

    @NonNull
    private final Map<String, String> env;

    @Nullable
    private final String script;

    public StartOptions() {
        this.args = new ArrayList<>();
        this.env = new HashMap<>();
        this.script = null;
    }

    public StartOptions(@NonNull PluginCall call) {
        this.args = StartOptions.getArgsFromCall(call);
        this.env = StartOptions.getEnvFromCall(call);
        this.script = call.getString("script");
    }

    @NonNull
    public List<String> getArgs() {
        return args;
    }

    @NonNull
    public Map<String, String> getEnv() {
        return env;
    }

    @Nullable
    public String getScript() {
        return script;
    }

    @NonNull
    private static List<String> getArgsFromCall(@NonNull PluginCall call) {
        List<String> args = new ArrayList<>();
        try {
            List<Object> values = call.getArray("args", new com.getcapacitor.JSArray()).toList();
            for (Object value : values) {
                args.add(value.toString());
            }
        } catch (Exception exception) {
            // No args provided.
        }
        return args;
    }

    @NonNull
    private static Map<String, String> getEnvFromCall(@NonNull PluginCall call) {
        Map<String, String> env = new HashMap<>();
        JSObject envObject = call.getObject("env");
        if (envObject != null) {
            Iterator<String> keys = envObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = envObject.getString(key);
                if (value != null) {
                    env.put(key, value);
                }
            }
        }
        return env;
    }
}
