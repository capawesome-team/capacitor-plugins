package io.capawesome.capacitorjs.plugins.appicon.classes;

public class CustomExceptions {

    public static final CustomException CHANGE_FAILED = new CustomException("CHANGE_FAILED", "The app icon could not be changed.");
    public static final CustomException ICON_MISSING = new CustomException(null, "icon must be provided.");
    public static final CustomException ICON_NOT_FOUND = new CustomException(
        "ICON_NOT_FOUND",
        "The alternate icon with the given name could not be found."
    );
}
