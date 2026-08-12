package io.capawesome.capacitorjs.plugins.androidintentlauncher.classes;

public class CustomExceptions {

    public static final CustomException ACTION_MISSING = new CustomException(null, "action must be provided.");
    public static final CustomException ACTIVITY_NOT_FOUND = new CustomException(
        "ACTIVITY_NOT_FOUND",
        "No activity was found that can handle the intent."
    );
    public static final CustomException START_FAILED = new CustomException("START_FAILED", "The activity could not be started.");
}
