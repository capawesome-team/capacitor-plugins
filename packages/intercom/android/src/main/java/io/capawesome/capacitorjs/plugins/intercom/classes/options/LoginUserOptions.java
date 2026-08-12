package io.capawesome.capacitorjs.plugins.intercom.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.intercom.classes.CustomExceptions;

public class LoginUserOptions {

    @Nullable
    private final String email;

    @Nullable
    private final String userId;

    public LoginUserOptions(@NonNull PluginCall call) throws Exception {
        this.userId = call.getString("userId");
        this.email = call.getString("email");
        if (userId == null && email == null) {
            throw CustomExceptions.USER_ID_OR_EMAIL_MISSING;
        }
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    @Nullable
    public String getUserId() {
        return userId;
    }
}
