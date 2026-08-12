package io.capawesome.capacitorjs.plugins.mailcomposer;

import android.content.Intent;
import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.mailcomposer.classes.CustomException;
import io.capawesome.capacitorjs.plugins.mailcomposer.classes.options.ComposeMailOptions;
import io.capawesome.capacitorjs.plugins.mailcomposer.classes.results.CanComposeMailResult;
import io.capawesome.capacitorjs.plugins.mailcomposer.classes.results.ComposeMailResult;
import io.capawesome.capacitorjs.plugins.mailcomposer.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.mailcomposer.interfaces.Result;

@CapacitorPlugin(name = "MailComposer")
public class MailComposerPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "MailComposerPlugin";

    private MailComposer implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new MailComposer(this);
    }

    @PluginMethod
    public void canComposeMail(PluginCall call) {
        try {
            NonEmptyResultCallback<CanComposeMailResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull CanComposeMailResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.canComposeMail(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void composeMail(PluginCall call) {
        try {
            ComposeMailOptions options = new ComposeMailOptions(call);
            Intent intent = implementation.createComposeIntent(options);
            if (implementation.canResolveIntent(intent)) {
                startActivityForResult(call, intent, "handleComposeMailResult");
            } else {
                rejectCallAsUnavailable(call);
            }
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @ActivityCallback
    private void handleComposeMailResult(PluginCall call, ActivityResult result) {
        if (call == null) {
            return;
        }
        resolveCall(call, new ComposeMailResult("unknown"));
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        String code = null;
        if (exception instanceof CustomException) {
            code = ((CustomException) exception).getCode();
        }
        Logger.error(TAG, message, exception);
        call.reject(message, code);
    }

    private void rejectCallAsUnavailable(@NonNull PluginCall call) {
        call.unavailable("This method is not available on this platform.");
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
