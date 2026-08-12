package io.capawesome.capacitorjs.plugins.clipboard.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.clipboard.classes.CustomExceptions;

public class WriteOptions {

    @Nullable
    private final String html;

    @Nullable
    private final String image;

    @Nullable
    private final String label;

    @Nullable
    private final String text;

    @Nullable
    private final String url;

    public WriteOptions(@NonNull PluginCall call) throws Exception {
        this.html = call.getString("html");
        this.image = call.getString("image");
        this.label = call.getString("label");
        this.text = call.getString("text");
        this.url = call.getString("url");
        if (html == null && image == null && text == null && url == null) {
            throw CustomExceptions.CONTENT_MISSING;
        }
    }

    @Nullable
    public String getHtml() {
        return html;
    }

    @Nullable
    public String getImage() {
        return image;
    }

    @Nullable
    public String getLabel() {
        return label;
    }

    @Nullable
    public String getText() {
        return text;
    }

    @Nullable
    public String getUrl() {
        return url;
    }
}
