package io.capawesome.capacitorjs.plugins.phonedialer.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.phonedialer.classes.CustomExceptions;

public class DialOptions {

    @NonNull
    private final String number;

    public DialOptions(@NonNull PluginCall call) throws Exception {
        String number = call.getString("number");
        if (number == null) {
            throw CustomExceptions.NUMBER_MISSING;
        }
        String sanitized = number.replaceAll("[^0-9+*#]", "");
        if (sanitized.isEmpty()) {
            throw CustomExceptions.NUMBER_INVALID;
        }
        this.number = sanitized;
    }

    @NonNull
    public String getNumber() {
        return number;
    }
}
