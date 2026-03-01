package io.capawesome.capacitorjs.plugins.googlesignin.classes;

public class CustomExceptions {

    public static final CustomException CLIENT_ID_MISSING = new CustomException(null, "clientId must be provided.");
    public static final CustomException NOT_INITIALIZED = new CustomException(
        null,
        "Google Sign-In is not initialized. Call initialize() first."
    );
    public static final CustomException SIGN_IN_CANCELED = new CustomException("SIGN_IN_CANCELED", "The user canceled the sign-in flow.");
}
