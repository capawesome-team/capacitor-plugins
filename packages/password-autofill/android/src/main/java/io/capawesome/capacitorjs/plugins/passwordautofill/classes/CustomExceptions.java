package io.capawesome.capacitorjs.plugins.passwordautofill.classes;

import androidx.annotation.NonNull;

public class CustomExceptions {

    public static final CustomException CANCELED = new CustomException("CANCELED", "The user canceled the save operation.");
    public static final CustomException PASSWORD_MISSING = new CustomException(null, "password must be provided.");
    public static final CustomException USERNAME_MISSING = new CustomException(null, "username must be provided.");

    @NonNull
    public static CustomException saveFailed(@NonNull String message) {
        return new CustomException("SAVE_FAILED", message);
    }
}
