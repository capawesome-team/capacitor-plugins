package io.capawesome.capacitorjs.plugins.dialog.classes;

public class CustomExceptions {

    public static final CustomException CANCELED = new CustomException("CANCELED", "The user canceled the dialog.");
    public static final CustomException MESSAGE_MISSING = new CustomException(null, "message must be provided.");
}
