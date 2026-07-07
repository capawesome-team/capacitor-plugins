package io.capawesome.capacitorjs.plugins.screenreader.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.screenreader.classes.CustomExceptions;

public class AnnounceOptions {

    @NonNull
    private final String value;

    @Nullable
    private final String language;

    public AnnounceOptions(@NonNull PluginCall call) throws Exception {
        String value = call.getString("value");
        if (value == null) {
            throw CustomExceptions.VALUE_MISSING;
        }
        this.value = value;
        this.language = call.getString("language");
    }

    @Nullable
    public String getLanguage() {
        return language;
    }

    @NonNull
    public String getValue() {
        return value;
    }
}
