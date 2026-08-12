package io.capawesome.capacitorjs.plugins.crisp.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.crisp.classes.CustomExceptions;

public class OpenHelpdeskArticleOptions {

    @Nullable
    private final String category;

    @NonNull
    private final String id;

    @NonNull
    private final String locale;

    @Nullable
    private final String title;

    public OpenHelpdeskArticleOptions(@NonNull PluginCall call) throws Exception {
        String id = call.getString("id");
        if (id == null) {
            throw CustomExceptions.ID_MISSING;
        }
        String locale = call.getString("locale");
        if (locale == null) {
            throw CustomExceptions.LOCALE_MISSING;
        }
        this.id = id;
        this.locale = locale;
        this.category = call.getString("category");
        this.title = call.getString("title");
    }

    @Nullable
    public String getCategory() {
        return category;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getLocale() {
        return locale;
    }

    @Nullable
    public String getTitle() {
        return title;
    }
}
