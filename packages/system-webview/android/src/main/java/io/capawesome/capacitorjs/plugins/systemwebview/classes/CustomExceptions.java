package io.capawesome.capacitorjs.plugins.systemwebview.classes;

public class CustomExceptions {

    public static final CustomException MIN_MAJOR_VERSION_MISSING = new CustomException(null, "minMajorVersion must be provided.");
    public static final CustomException OPEN_FAILED = new CustomException("OPEN_FAILED", "The Play Store could not be opened.");
    public static final CustomException VERSION_UNPARSEABLE = new CustomException(
        "VERSION_UNPARSEABLE",
        "The WebView version name is not a Chromium-style version and could not be parsed."
    );
    public static final CustomException WEB_VIEW_PACKAGE_UNAVAILABLE = new CustomException(
        "WEB_VIEW_PACKAGE_UNAVAILABLE",
        "The active WebView package could not be determined."
    );
}
