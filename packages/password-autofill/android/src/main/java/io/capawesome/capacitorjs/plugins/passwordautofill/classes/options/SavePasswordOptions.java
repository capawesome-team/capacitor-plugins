package io.capawesome.capacitorjs.plugins.passwordautofill.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.passwordautofill.classes.CustomExceptions;

public class SavePasswordOptions {

    @NonNull
    private final String password;

    @NonNull
    private final String username;

    public SavePasswordOptions(@NonNull PluginCall call) throws Exception {
        this.password = getPasswordFromCall(call);
        this.username = getUsernameFromCall(call);
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    @NonNull
    private static String getPasswordFromCall(@NonNull PluginCall call) throws Exception {
        String password = call.getString("password");
        if (password == null) {
            throw CustomExceptions.PASSWORD_MISSING;
        }
        return password;
    }

    @NonNull
    private static String getUsernameFromCall(@NonNull PluginCall call) throws Exception {
        String username = call.getString("username");
        if (username == null) {
            throw CustomExceptions.USERNAME_MISSING;
        }
        return username;
    }
}
