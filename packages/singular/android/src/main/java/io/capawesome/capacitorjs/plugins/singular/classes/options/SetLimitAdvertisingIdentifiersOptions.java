package io.capawesome.capacitorjs.plugins.singular.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.singular.classes.CustomExceptions;

public class SetLimitAdvertisingIdentifiersOptions {

    private final boolean limit;

    public SetLimitAdvertisingIdentifiersOptions(@NonNull PluginCall call) throws Exception {
        Boolean limit = call.getBoolean("limit");
        if (limit == null) {
            throw CustomExceptions.LIMIT_MISSING;
        }
        this.limit = limit;
    }

    public boolean isLimit() {
        return limit;
    }
}
