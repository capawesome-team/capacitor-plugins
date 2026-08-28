package io.capawesome.capacitorjs.plugins.flic.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.flic.classes.CustomExceptions;

public class ForgetButtonByIdOptions {

    @NonNull
    private final String id;

    public ForgetButtonByIdOptions(@NonNull PluginCall call) throws Exception {
        this.id = ForgetButtonByIdOptions.getIdFromCall(call);
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
