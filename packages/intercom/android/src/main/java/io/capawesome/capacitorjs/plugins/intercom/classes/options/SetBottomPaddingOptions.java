package io.capawesome.capacitorjs.plugins.intercom.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.intercom.classes.CustomExceptions;

public class SetBottomPaddingOptions {

    private final int padding;

    public SetBottomPaddingOptions(@NonNull PluginCall call) throws Exception {
        Integer padding = call.getInt("padding");
        if (padding == null) {
            throw CustomExceptions.PADDING_MISSING;
        }
        this.padding = padding;
    }

    public int getPadding() {
        return padding;
    }
}
