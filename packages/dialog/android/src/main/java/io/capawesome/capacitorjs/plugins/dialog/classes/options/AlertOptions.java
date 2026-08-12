package io.capawesome.capacitorjs.plugins.dialog.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.dialog.classes.CustomExceptions;

public class AlertOptions {

    @NonNull
    private final String buttonTitle;

    @NonNull
    private final String message;

    @Nullable
    private final String title;

    public AlertOptions(@NonNull PluginCall call) throws Exception {
        String message = call.getString("message");
        if (message == null) {
            throw CustomExceptions.MESSAGE_MISSING;
        }
        this.message = message;
        this.buttonTitle = call.getString("buttonTitle", "OK");
        this.title = call.getString("title");
    }

    @NonNull
    public String getButtonTitle() {
        return buttonTitle;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    @Nullable
    public String getTitle() {
        return title;
    }
}
