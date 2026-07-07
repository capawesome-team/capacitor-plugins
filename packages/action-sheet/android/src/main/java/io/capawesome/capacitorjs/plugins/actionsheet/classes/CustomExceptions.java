package io.capawesome.capacitorjs.plugins.actionsheet.classes;

public class CustomExceptions {

    public static final CustomException BUTTON_TITLE_MISSING = new CustomException(null, "each button must provide a title.");
    public static final CustomException OPTIONS_EMPTY = new CustomException(null, "options must not be empty.");
    public static final CustomException OPTIONS_MISSING = new CustomException(null, "options must be provided.");
}
