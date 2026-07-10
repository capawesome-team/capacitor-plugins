package io.capawesome.capacitorjs.plugins.nodejs.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.nodejs.classes.CustomExceptions;

public class SendOptions {

    @NonNull
    private final JSArray args;

    @NonNull
    private final String eventName;

    public SendOptions(@NonNull PluginCall call) throws Exception {
        this.args = SendOptions.getArgsFromCall(call);
        this.eventName = SendOptions.getEventNameFromCall(call);
    }

    @NonNull
    public JSArray getArgs() {
        return args;
    }

    @NonNull
    public String getEventName() {
        return eventName;
    }

    @NonNull
    private static JSArray getArgsFromCall(@NonNull PluginCall call) {
        JSArray args = call.getArray("args");
        if (args == null) {
            args = new JSArray();
        }
        return args;
    }

    @NonNull
    private static String getEventNameFromCall(@NonNull PluginCall call) throws Exception {
        String eventName = call.getString("eventName");
        if (eventName == null) {
            throw CustomExceptions.EVENT_NAME_MISSING;
        }
        return eventName;
    }
}
