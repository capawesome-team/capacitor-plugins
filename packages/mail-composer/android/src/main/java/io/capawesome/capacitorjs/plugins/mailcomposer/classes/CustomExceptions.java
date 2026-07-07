package io.capawesome.capacitorjs.plugins.mailcomposer.classes;

public class CustomExceptions {

    public static final CustomException ATTACHMENT_NOT_FOUND = new CustomException(
        "ATTACHMENT_NOT_FOUND",
        "An attachment could not be found at the provided path."
    );
}
