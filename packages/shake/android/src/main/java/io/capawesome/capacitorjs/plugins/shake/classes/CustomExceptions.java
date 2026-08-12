package io.capawesome.capacitorjs.plugins.shake.classes;

public class CustomExceptions {

    public static final CustomException INVALID_SENSITIVITY = new CustomException(
        null,
        "sensitivity must be one of 'light', 'medium' or 'hard'."
    );
}
