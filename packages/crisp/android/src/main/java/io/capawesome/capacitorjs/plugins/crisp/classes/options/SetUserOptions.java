package io.capawesome.capacitorjs.plugins.crisp.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class SetUserOptions {

    @Nullable
    private final String avatarUrl;

    @Nullable
    private final String email;

    @Nullable
    private final String emailSignature;

    @Nullable
    private final String nickname;

    @Nullable
    private final String phone;

    public SetUserOptions(@NonNull PluginCall call) {
        this.avatarUrl = call.getString("avatarUrl");
        this.email = call.getString("email");
        this.emailSignature = call.getString("emailSignature");
        this.nickname = call.getString("nickname");
        this.phone = call.getString("phone");
    }

    @Nullable
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    @Nullable
    public String getEmailSignature() {
        return emailSignature;
    }

    @Nullable
    public String getNickname() {
        return nickname;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }
}
