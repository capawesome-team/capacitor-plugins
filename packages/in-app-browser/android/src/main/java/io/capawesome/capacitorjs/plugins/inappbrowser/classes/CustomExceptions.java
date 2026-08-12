package io.capawesome.capacitorjs.plugins.inappbrowser.classes;

public class CustomExceptions {

    public static final CustomException BACKGROUND_COLOR_INVALID = new CustomException(
        null,
        "backgroundColor must be a valid hex color code."
    );
    public static final CustomException BROWSER_NOT_FOUND = new CustomException(
        "BROWSER_NOT_FOUND",
        "No app was found on the device that can open the URL."
    );
    public static final CustomException COLOR_INVALID = new CustomException(null, "color must be a valid hex color code.");
    public static final CustomException DATA_MISSING = new CustomException(null, "data must be provided.");
    public static final CustomException NO_BROWSER_OPEN = new CustomException("NO_BROWSER_OPEN", "No browser is currently open.");
    public static final CustomException SCRIPT_MISSING = new CustomException(null, "script must be provided.");
    public static final CustomException TOOLBAR_COLOR_INVALID = new CustomException(null, "toolbarColor must be a valid hex color code.");
    public static final CustomException URL_INVALID = new CustomException(null, "url is invalid.");
    public static final CustomException URL_MISSING = new CustomException(null, "url must be provided.");
}
