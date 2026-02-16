package io.capawesome.capacitorjs.plugins.googlesignin.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class SignInOptions {

    @Nullable
    private final String nonce;

    public SignInOptions(@NonNull PluginCall call) {
        this.nonce = getNonceFromCall(call);
    }

    @Nullable
    public String getNonce() {
        return nonce;
    }

    @Nullable
    private static String getNonceFromCall(@NonNull PluginCall call) {
        return call.getString("nonce");
    }
}
