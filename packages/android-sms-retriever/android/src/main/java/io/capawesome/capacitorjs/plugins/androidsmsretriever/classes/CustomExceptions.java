package io.capawesome.capacitorjs.plugins.androidsmsretriever.classes;

public class CustomExceptions {

    public static final CustomException CANCELED = new CustomException("CANCELED", "The operation was canceled.");
    public static final CustomException OPERATION_IN_PROGRESS = new CustomException(null, "An SMS retrieval is already in progress.");
    public static final CustomException PHONE_NUMBER_HINT_FAILED = new CustomException(
        "PHONE_NUMBER_HINT_FAILED",
        "The phone number could not be requested."
    );
    public static final CustomException RETRIEVE_FAILED = new CustomException("RETRIEVE_FAILED", "The SMS message could not be retrieved.");
    public static final CustomException TIMEOUT = new CustomException("TIMEOUT", "No SMS message was received within the time window.");
    public static final CustomException USER_DENIED = new CustomException(
        "USER_DENIED",
        "The user denied consent to read the SMS message."
    );
}
