package io.capawesome.capacitorjs.plugins.settingslauncher.classes;

public class CustomExceptions {

    public static final CustomException OPEN_FAILED = new CustomException("OPEN_FAILED", "The settings screen could not be opened.");
    public static final CustomException PAGE_INVALID = new CustomException(null, "page must be one of the supported values.");
    public static final CustomException PAGE_MISSING = new CustomException(null, "page must be provided.");
    public static final CustomException PAGE_NOT_SUPPORTED = new CustomException(
        "PAGE_NOT_SUPPORTED",
        "The requested settings screen is not supported on this device."
    );
}
