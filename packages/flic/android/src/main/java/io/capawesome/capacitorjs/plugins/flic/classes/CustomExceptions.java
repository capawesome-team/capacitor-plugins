package io.capawesome.capacitorjs.plugins.flic.classes;

public class CustomExceptions {

    public static final CustomException BUTTON_NOT_FOUND = new CustomException(null, "No button with the provided id was found.");
    public static final CustomException ID_MISSING = new CustomException(null, "id must be provided.");
    public static final CustomException NOT_INITIALIZED = new CustomException(
        null,
        "The plugin is not initialized. Call `initialize()` first."
    );
    public static final CustomException PERMISSIONS_DENIED = new CustomException(null, "The required permissions have not been granted.");
    public static final CustomException SCAN_ALREADY_RUNNING = new CustomException(null, "A scan is already running.");
    public static final CustomException SCAN_STOPPED = new CustomException(null, "The scan has been stopped.");
}
