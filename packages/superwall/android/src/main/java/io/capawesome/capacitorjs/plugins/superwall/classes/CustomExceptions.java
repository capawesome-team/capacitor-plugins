package io.capawesome.capacitorjs.plugins.superwall.classes;

public class CustomExceptions {

    public static final CustomException API_KEY_MISSING = new CustomException(null, "apiKey must be provided.");
    public static final CustomException ATTRIBUTES_MISSING = new CustomException(null, "attributes must be provided.");
    public static final CustomException FAILED_TO_GET_APPLICATION_CONTEXT = new CustomException(null, "Failed to get Application context.");
    public static final CustomException FAILED_TO_GET_PRESENTATION_RESULT = new CustomException(null, "Failed to get presentation result.");
    public static final CustomException NOT_CONFIGURED = new CustomException(
        null,
        "Superwall SDK is not configured. Call configure() first."
    );
    public static final CustomException PLACEMENT_MISSING = new CustomException(null, "placement must be provided.");
    public static final CustomException UNKNOWN_ERROR = new CustomException(null, "An unknown error occurred.");
    public static final CustomException URL_MISSING = new CustomException(null, "url must be provided.");
    public static final CustomException USER_ID_MISSING = new CustomException(null, "userId must be provided.");
}
