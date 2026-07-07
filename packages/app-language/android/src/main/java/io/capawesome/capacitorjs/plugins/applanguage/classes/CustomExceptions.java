package io.capawesome.capacitorjs.plugins.applanguage.classes;

public class CustomExceptions {

    public static final CustomException LANGUAGE_TAG_INVALID = new CustomException(
        null,
        "languageTag must be a valid BCP 47 language tag."
    );
    public static final CustomException LANGUAGE_TAG_MISSING = new CustomException(null, "languageTag must be provided.");
}
