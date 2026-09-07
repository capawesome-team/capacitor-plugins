package io.capawesome.capacitorjs.plugins.tiktokappevents.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.tiktokappevents.classes.CustomExceptions;

public class IdentifyOptions {

    @Nullable
    private final String email;

    @NonNull
    private final String externalId;

    @Nullable
    private final String externalUserName;

    @Nullable
    private final String phoneNumber;

    public IdentifyOptions(@NonNull PluginCall call) throws Exception {
        String externalId = call.getString("externalId");
        if (externalId == null) {
            throw CustomExceptions.EXTERNAL_ID_MISSING;
        }
        this.externalId = externalId;
        this.email = call.getString("email");
        this.externalUserName = call.getString("externalUserName");
        this.phoneNumber = call.getString("phoneNumber");
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getExternalId() {
        return externalId;
    }

    @Nullable
    public String getExternalUserName() {
        return externalUserName;
    }

    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
