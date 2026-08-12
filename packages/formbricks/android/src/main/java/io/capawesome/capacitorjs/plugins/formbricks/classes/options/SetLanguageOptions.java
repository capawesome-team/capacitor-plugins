package io.capawesome.capacitorjs.plugins.formbricks.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.formbricks.FormbricksPlugin;

public class SetLanguageOptions {

    @NonNull
    private final String language;

    public SetLanguageOptions(@NonNull PluginCall call) throws Exception {
        String language = call.getString("language");
        if (language == null) {
            throw new Exception(FormbricksPlugin.ERROR_LANGUAGE_MISSING);
        }
        this.language = language;
    }

    @NonNull
    public String getLanguage() {
        return language;
    }
}
