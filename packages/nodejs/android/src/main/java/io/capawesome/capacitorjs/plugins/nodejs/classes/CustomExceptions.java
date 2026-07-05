package io.capawesome.capacitorjs.plugins.nodejs.classes;

public class CustomExceptions {

    public static final CustomException EVENT_NAME_MISSING = new CustomException("EVENT_NAME_MISSING", "eventName must be provided.");
    public static final CustomException NODE_ALREADY_RUNNING = new CustomException(
        "NODE_ALREADY_RUNNING",
        "The Node.js runtime is already running."
    );
    public static final CustomException NODE_NOT_READY = new CustomException(
        "NODE_NOT_READY",
        "The Node.js runtime is not ready to receive messages."
    );
    public static final CustomException PROJECT_NOT_FOUND = new CustomException(
        "PROJECT_NOT_FOUND",
        "The Node.js project directory was not found."
    );
    public static final CustomException SCRIPT_NOT_FOUND = new CustomException("SCRIPT_NOT_FOUND", "The script file was not found.");
    public static final CustomException START_MODE_NOT_MANUAL = new CustomException(
        "START_MODE_NOT_MANUAL",
        "The startMode configuration option must be set to 'manual'."
    );
}
