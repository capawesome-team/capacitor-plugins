package io.capawesome.capacitorjs.plugins.mailcomposer.classes;

public class CustomExceptions {

    public static final CustomException ATTACHMENT_DATA_INVALID = new CustomException(null, "data must be a valid base64 string.");
    public static final CustomException ATTACHMENT_DATA_OR_PATH_MISSING = new CustomException(
        null,
        "Either data or path must be provided for an attachment."
    );
    public static final CustomException ATTACHMENT_NAME_MISSING = new CustomException(null, "name must be provided if data is provided.");
    public static final CustomException ATTACHMENT_NOT_FOUND = new CustomException(
        "ATTACHMENT_NOT_FOUND",
        "An attachment could not be found at the provided path."
    );
}
