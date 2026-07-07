package io.capawesome.capacitorjs.plugins.mapslauncher.classes;

public class CustomExceptions {

    public static final CustomException APP_NOT_AVAILABLE = new CustomException(
        "APP_NOT_AVAILABLE",
        "The requested navigation app is not installed or cannot be launched."
    );
    public static final CustomException DESTINATION_INVALID = new CustomException(
        null,
        "destination must contain either coordinates or an address, but not both."
    );
    public static final CustomException DESTINATION_MISSING = new CustomException(null, "destination must be provided.");
    public static final CustomException LAUNCH_FAILED = new CustomException("LAUNCH_FAILED", "The navigation app could not be launched.");
}
