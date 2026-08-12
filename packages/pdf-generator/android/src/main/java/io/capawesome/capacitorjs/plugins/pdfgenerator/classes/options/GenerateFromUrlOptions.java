package io.capawesome.capacitorjs.plugins.pdfgenerator.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.pdfgenerator.classes.CustomExceptions;

public class GenerateFromUrlOptions extends GenerateOptions {

    @NonNull
    private final String url;

    public GenerateFromUrlOptions(@NonNull PluginCall call) throws Exception {
        super(call);
        this.url = GenerateFromUrlOptions.getUrlFromCall(call);
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    @NonNull
    private static String getUrlFromCall(@NonNull PluginCall call) throws Exception {
        String url = call.getString("url");
        if (url == null) {
            throw CustomExceptions.URL_MISSING;
        }
        return url;
    }
}
