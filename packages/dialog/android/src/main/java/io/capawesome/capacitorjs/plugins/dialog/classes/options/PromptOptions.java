package io.capawesome.capacitorjs.plugins.dialog.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.dialog.classes.CustomExceptions;

public class PromptOptions {

    @NonNull
    private final String cancelButtonTitle;

    @Nullable
    private final String inputPlaceholder;

    @Nullable
    private final String inputText;

    @NonNull
    private final String message;

    @NonNull
    private final String okButtonTitle;

    @Nullable
    private final String title;

    public PromptOptions(@NonNull PluginCall call) throws Exception {
        String message = call.getString("message");
        if (message == null) {
            throw CustomExceptions.MESSAGE_MISSING;
        }
        this.message = message;
        this.cancelButtonTitle = call.getString("cancelButtonTitle", "Cancel");
        this.inputPlaceholder = call.getString("inputPlaceholder");
        this.inputText = call.getString("inputText");
        this.okButtonTitle = call.getString("okButtonTitle", "OK");
        this.title = call.getString("title");
    }

    @NonNull
    public String getCancelButtonTitle() {
        return cancelButtonTitle;
    }

    @Nullable
    public String getInputPlaceholder() {
        return inputPlaceholder;
    }

    @Nullable
    public String getInputText() {
        return inputText;
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
