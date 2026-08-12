package io.capawesome.capacitorjs.plugins.androidsmsretriever.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class RetrieveSmsOptions {

    @Nullable
    private final String senderPhoneNumber;

    public RetrieveSmsOptions(@NonNull PluginCall call) {
        this.senderPhoneNumber = getSenderPhoneNumberFromCall(call);
    }

    @Nullable
    public String getSenderPhoneNumber() {
        return senderPhoneNumber;
    }

    @Nullable
    private static String getSenderPhoneNumberFromCall(@NonNull PluginCall call) {
        return call.getString("senderPhoneNumber");
    }
}
