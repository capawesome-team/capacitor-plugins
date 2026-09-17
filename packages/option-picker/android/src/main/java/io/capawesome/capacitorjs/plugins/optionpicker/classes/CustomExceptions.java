package io.capawesome.capacitorjs.plugins.optionpicker.classes;

public class CustomExceptions {

    public static final CustomException OPTION_LABEL_MISSING = new CustomException(null, "each option must provide a label.");
    public static final CustomException OPTION_VALUE_MISSING = new CustomException(null, "each option must provide a value.");
    public static final CustomException OPTIONS_EMPTY = new CustomException(null, "options must not be empty.");
    public static final CustomException OPTIONS_MISSING = new CustomException(null, "options must be provided.");
    public static final CustomException PICKER_ALREADY_PRESENTED = new CustomException(null, "A picker is already presented.");
    public static final CustomException PICKER_CANCELED = new CustomException("CANCELED", "The user canceled the picker.");
    public static final CustomException THEME_INVALID = new CustomException(null, "theme is invalid.");
}
