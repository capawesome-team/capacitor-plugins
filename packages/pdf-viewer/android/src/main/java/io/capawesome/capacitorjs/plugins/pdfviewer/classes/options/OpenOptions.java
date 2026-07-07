package io.capawesome.capacitorjs.plugins.pdfviewer.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.pdfviewer.classes.CustomExceptions;

public class OpenOptions {

    private final int page;

    @Nullable
    private final String password;

    @NonNull
    private final String path;

    @Nullable
    private final String title;

    public OpenOptions(@NonNull PluginCall call) throws Exception {
        String path = call.getString("path");
        if (path == null) {
            throw CustomExceptions.PATH_MISSING;
        }
        this.path = path;
        this.page = call.getInt("page", 1);
        this.password = call.getString("password");
        this.title = call.getString("title");
    }

    public int getPage() {
        return page;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    @NonNull
    public String getPath() {
        return path;
    }

    @Nullable
    public String getTitle() {
        return title;
    }
}
