package io.capawesome.capacitorjs.plugins.inappbrowser.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.CustomExceptions;

public class ExecuteScriptOptions {

    @NonNull
    private final String script;

    public ExecuteScriptOptions(@NonNull PluginCall call) throws Exception {
        this.script = ExecuteScriptOptions.getScriptFromCall(call);
    }

    @NonNull
    public String getScript() {
        return script;
    }

    @NonNull
    private static String getScriptFromCall(@NonNull PluginCall call) throws Exception {
        String script = call.getString("script");
        if (script == null || script.isEmpty()) {
            throw CustomExceptions.SCRIPT_MISSING;
        }
        return script;
    }
}
