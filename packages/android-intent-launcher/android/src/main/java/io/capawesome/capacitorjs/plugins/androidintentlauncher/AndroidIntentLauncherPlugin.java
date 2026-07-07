package io.capawesome.capacitorjs.plugins.androidintentlauncher;

import android.content.ActivityNotFoundException;
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
import io.capawesome.capacitorjs.plugins.androidintentlauncher.classes.CustomException;
import io.capawesome.capacitorjs.plugins.androidintentlauncher.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.androidintentlauncher.classes.options.IntentOptions;
import io.capawesome.capacitorjs.plugins.androidintentlauncher.classes.results.CanResolveActivityResult;
import io.capawesome.capacitorjs.plugins.androidintentlauncher.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.androidintentlauncher.interfaces.Result;

@CapacitorPlugin(name = "AndroidIntentLauncher")
public class AndroidIntentLauncherPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";
    public static final String TAG = "AndroidIntentLauncherPlugin";

    private AndroidIntentLauncher implementation;

    @Override
    public void load() {
        implementation = new AndroidIntentLauncher(this);
    }

    @PluginMethod
    public void canResolveActivity(PluginCall call) {
        try {
            IntentOptions options = new IntentOptions(call);
            NonEmptyResultCallback<CanResolveActivityResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull CanResolveActivityResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.canResolveActivity(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void startActivity(PluginCall call) {
        Intent intent;
        try {
            IntentOptions options = new IntentOptions(call);
            intent = implementation.createIntent(options);
        } catch (Exception exception) {
            rejectCall(call, exception);
            return;
        }
        try {
            startActivityForResult(call, intent, "handleStartActivityResult");
        } catch (ActivityNotFoundException exception) {
            rejectCall(call, CustomExceptions.ACTIVITY_NOT_FOUND);
        } catch (Exception exception) {
            rejectCall(call, CustomExceptions.START_FAILED);
        }
    }

    @ActivityCallback
    private void handleStartActivityResult(@Nullable PluginCall call, @NonNull ActivityResult result) {
        if (call == null) {
            return;
        }
        resolveCall(call, implementation.createStartActivityResult(result));
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

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
