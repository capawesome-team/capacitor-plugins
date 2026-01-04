package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.CustomExceptions;

public class RetryConnectionOptions {

    @NonNull
    private final String serialNumber;

    public RetryConnectionOptions(@NonNull PluginCall call) throws Exception {
        this.serialNumber = RetryConnectionOptions.getSerialNumberFromCall(call);
    }

    @NonNull
    public String getSerialNumber() {
        return serialNumber;
    }

    @NonNull
    private static String getSerialNumberFromCall(@NonNull PluginCall call) throws Exception {
        String serialNumber = call.getString("serialNumber");
        if (serialNumber == null) {
            throw CustomExceptions.SERIAL_NUMBER_MISSING;
        }
        return serialNumber;
    }
}
