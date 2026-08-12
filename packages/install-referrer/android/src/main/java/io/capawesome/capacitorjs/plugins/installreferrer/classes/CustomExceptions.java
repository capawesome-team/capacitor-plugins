package io.capawesome.capacitorjs.plugins.installreferrer.classes;

public class CustomExceptions {

    public static final CustomException DEVELOPER_ERROR = new CustomException(
        "DEVELOPER_ERROR",
        "The install referrer request failed due to a developer error."
    );
    public static final CustomException FEATURE_NOT_SUPPORTED = new CustomException(
        "FEATURE_NOT_SUPPORTED",
        "The install referrer API is not supported by the installed Play Store app."
    );
    public static final CustomException SERVICE_UNAVAILABLE = new CustomException(
        "SERVICE_UNAVAILABLE",
        "The install referrer service is currently unavailable."
    );
}
