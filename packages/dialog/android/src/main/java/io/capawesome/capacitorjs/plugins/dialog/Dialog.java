package io.capawesome.capacitorjs.plugins.dialog;

import android.app.Activity;
import android.text.InputType;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import io.capawesome.capacitorjs.plugins.dialog.classes.options.AlertOptions;
import io.capawesome.capacitorjs.plugins.dialog.classes.options.ConfirmOptions;
import io.capawesome.capacitorjs.plugins.dialog.classes.options.PromptOptions;
import io.capawesome.capacitorjs.plugins.dialog.classes.results.ConfirmResult;
import io.capawesome.capacitorjs.plugins.dialog.classes.results.PromptResult;
import io.capawesome.capacitorjs.plugins.dialog.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.dialog.interfaces.NonEmptyResultCallback;

public class Dialog {

    @NonNull
    private final DialogPlugin plugin;

    public Dialog(@NonNull DialogPlugin plugin) {
        this.plugin = plugin;
    }

    public void alert(@NonNull AlertOptions options, @NonNull EmptyCallback callback) {
        Activity activity = plugin.getActivity();
        activity.runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(options.getTitle());
            builder.setMessage(options.getMessage());
            builder.setPositiveButton(options.getButtonTitle(), (dialog, which) -> callback.success());
            builder.setOnCancelListener(dialog -> callback.success());
            builder.create().show();
        });
    }

    public void confirm(@NonNull ConfirmOptions options, @NonNull NonEmptyResultCallback<ConfirmResult> callback) {
        Activity activity = plugin.getActivity();
        activity.runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(options.getTitle());
            builder.setMessage(options.getMessage());
            builder.setPositiveButton(options.getOkButtonTitle(), (dialog, which) -> callback.success(new ConfirmResult(true)));
            builder.setNegativeButton(options.getCancelButtonTitle(), (dialog, which) -> callback.success(new ConfirmResult(false)));
            builder.setOnCancelListener(dialog -> callback.success(new ConfirmResult(false)));
            builder.create().show();
        });
    }

    public void prompt(@NonNull PromptOptions options, @NonNull NonEmptyResultCallback<PromptResult> callback) {
        Activity activity = plugin.getActivity();
        activity.runOnUiThread(() -> {
            EditText input = new EditText(activity);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            if (options.getInputPlaceholder() != null) {
                input.setHint(options.getInputPlaceholder());
            }
            if (options.getInputText() != null) {
                input.setText(options.getInputText());
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(options.getTitle());
            builder.setMessage(options.getMessage());
            builder.setView(input);
            builder.setPositiveButton(options.getOkButtonTitle(), (dialog, which) ->
                callback.success(new PromptResult(input.getText().toString(), false))
            );
            builder.setNegativeButton(options.getCancelButtonTitle(), (dialog, which) ->
                callback.success(new PromptResult(input.getText().toString(), true))
            );
            builder.setOnCancelListener(dialog -> callback.success(new PromptResult("", true)));
            builder.create().show();
        });
    }
}
