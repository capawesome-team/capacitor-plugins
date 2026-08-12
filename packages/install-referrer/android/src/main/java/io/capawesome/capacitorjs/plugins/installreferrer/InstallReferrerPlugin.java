package io.capawesome.capacitorjs.plugins.installreferrer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.installreferrer.classes.CustomException;
import io.capawesome.capacitorjs.plugins.installreferrer.classes.results.GetInstallReferrerResult;
import io.capawesome.capacitorjs.plugins.installreferrer.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.installreferrer.interfaces.Result;

@CapacitorPlugin(name = "InstallReferrer")
public class InstallReferrerPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "InstallReferrerPlugin";

    private InstallReferrer implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new InstallReferrer(this);
    }

    @PluginMethod
    public void getAttributionToken(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void getInstallReferrer(PluginCall call) {
        try {
            NonEmptyResultCallback<GetInstallReferrerResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetInstallReferrerResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getInstallReferrer(callback);
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

    private void rejectCallAsUnimplemented(@NonNull PluginCall call) {
        call.unimplemented("This method is not available on this platform.");
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
