package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.CustomExceptions;

public class ForgetReaderOptions {

    @NonNull
    private final String serialNumber;

    public ForgetReaderOptions(@NonNull PluginCall call) throws Exception {
        this.serialNumber = ForgetReaderOptions.getSerialNumberFromCall(call);
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
