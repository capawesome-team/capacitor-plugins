package io.capawesome.capacitorjs.plugins.pixlive.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.pixlive.classes.CustomExceptions;

public class SetInterfaceLanguageOptions {

    @NonNull
    private final String language;

    public SetInterfaceLanguageOptions(@NonNull PluginCall call) throws Exception {
        this.language = SetInterfaceLanguageOptions.getLanguageFromCall(call);
    }

    @NonNull
    public String getLanguage() {
        return language;
    }

    @NonNull
    private static String getLanguageFromCall(@NonNull PluginCall call) throws Exception {
        String language = call.getString("language");
        if (language == null) {
            throw CustomExceptions.LANGUAGE_MISSING;
        }
        return language;
    }
}
