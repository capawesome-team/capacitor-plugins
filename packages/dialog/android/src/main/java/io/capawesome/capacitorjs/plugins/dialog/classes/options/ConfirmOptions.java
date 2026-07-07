package io.capawesome.capacitorjs.plugins.dialog.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.dialog.classes.CustomExceptions;

public class ConfirmOptions {

    @NonNull
    private final String cancelButtonTitle;

    @NonNull
    private final String message;

    @NonNull
    private final String okButtonTitle;

    @Nullable
    private final String title;

    public ConfirmOptions(@NonNull PluginCall call) throws Exception {
        String message = call.getString("message");
        if (message == null) {
            throw CustomExceptions.MESSAGE_MISSING;
        }
        this.message = message;
        this.cancelButtonTitle = call.getString("cancelButtonTitle", "Cancel");
        this.okButtonTitle = call.getString("okButtonTitle", "OK");
        this.title = call.getString("title");
    }

    @NonNull
    public String getCancelButtonTitle() {
        return cancelButtonTitle;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    @NonNull
    public String getOkButtonTitle() {
        return okButtonTitle;
    }

    @Nullable
    public String getTitle() {
        return title;
    }
}
