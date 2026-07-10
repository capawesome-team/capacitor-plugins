package io.capawesome.capacitorjs.plugins.pdfgenerator.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.pdfgenerator.classes.CustomExceptions;

public class GenerateFromHtmlOptions extends GenerateOptions {

    @Nullable
    private final String baseUrl;

    @NonNull
    private final String html;

    public GenerateFromHtmlOptions(@NonNull PluginCall call) throws Exception {
        super(call);
        this.baseUrl = GenerateFromHtmlOptions.getBaseUrlFromCall(call);
        this.html = GenerateFromHtmlOptions.getHtmlFromCall(call);
    }

    @Nullable
    public String getBaseUrl() {
        return baseUrl;
    }

    @NonNull
    public String getHtml() {
        return html;
    }

    @Nullable
    private static String getBaseUrlFromCall(@NonNull PluginCall call) {
        return call.getString("baseUrl");
    }

    @NonNull
    private static String getHtmlFromCall(@NonNull PluginCall call) throws Exception {
        String html = call.getString("html");
        if (html == null) {
            throw CustomExceptions.HTML_MISSING;
        }
        return html;
    }
}
