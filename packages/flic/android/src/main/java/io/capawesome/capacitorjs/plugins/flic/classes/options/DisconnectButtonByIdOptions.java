package io.capawesome.capacitorjs.plugins.flic.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.flic.classes.CustomExceptions;

public class DisconnectButtonByIdOptions {

    @NonNull
    private final String id;

    public DisconnectButtonByIdOptions(@NonNull PluginCall call) throws Exception {
        this.id = DisconnectButtonByIdOptions.getIdFromCall(call);
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    private static String getIdFromCall(@NonNull PluginCall call) throws Exception {
        String id = call.getString("id");
        if (id == null) {
            throw CustomExceptions.ID_MISSING;
        }
        return id;
    }
}
