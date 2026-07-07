package io.capawesome.capacitorjs.plugins.wallet;

import androidx.annotation.NonNull;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.wallet.classes.CustomException;
import io.capawesome.capacitorjs.plugins.wallet.classes.options.SaveToGoogleWalletOptions;
import io.capawesome.capacitorjs.plugins.wallet.interfaces.EmptyCallback;

@CapacitorPlugin(name = "Wallet")
public class WalletPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String TAG = "WalletPlugin";

    private Wallet implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new Wallet(this);
    }

    @PluginMethod
    public void addPasses(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void canAddPasses(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void saveToGoogleWallet(PluginCall call) {
        try {
            SaveToGoogleWalletOptions options = new SaveToGoogleWalletOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.saveToGoogleWallet(options, callback);
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

    private void resolveCall(@NonNull PluginCall call) {
        call.resolve();
    }
}
