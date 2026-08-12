package io.capawesome.capacitorjs.plugins.actionsheet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.actionsheet.classes.CustomException;
import io.capawesome.capacitorjs.plugins.actionsheet.classes.options.ShowActionsOptions;
import io.capawesome.capacitorjs.plugins.actionsheet.classes.results.ShowActionsResult;
import io.capawesome.capacitorjs.plugins.actionsheet.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.actionsheet.interfaces.Result;

@CapacitorPlugin(name = "ActionSheet")
public class ActionSheetPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "ActionSheetPlugin";

    private ActionSheet implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new ActionSheet(this);
    }

    @PluginMethod
    public void showActions(PluginCall call) {
        try {
            ShowActionsOptions options = new ShowActionsOptions(call);
            NonEmptyResultCallback<ShowActionsResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull ShowActionsResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.showActions(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
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
