package io.capawesome.capacitorjs.plugins.applanguage.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.applanguage.classes.CustomExceptions;
import java.util.Locale;

public class SetLanguageOptions {

    @NonNull
    private final String languageTag;

    public SetLanguageOptions(@NonNull PluginCall call) throws Exception {
        this.languageTag = SetLanguageOptions.getLanguageTagFromCall(call);
    }

    @NonNull
    public String getLanguageTag() {
        return languageTag;
    }

    @NonNull
    private static String getLanguageTagFromCall(@NonNull PluginCall call) throws Exception {
        String languageTag = call.getString("languageTag");
        if (languageTag == null || languageTag.trim().isEmpty()) {
            throw CustomExceptions.LANGUAGE_TAG_MISSING;
        }
        if (Locale.forLanguageTag(languageTag).getLanguage().isEmpty()) {
            throw CustomExceptions.LANGUAGE_TAG_INVALID;
        }
        return languageTag;
    }
}
