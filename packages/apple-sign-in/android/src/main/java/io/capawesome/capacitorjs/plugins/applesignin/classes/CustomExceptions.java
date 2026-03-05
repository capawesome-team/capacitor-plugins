package io.capawesome.capacitorjs.plugins.applesignin.classes;

public class CustomExceptions {

    public static final CustomException CLIENT_ID_MISSING = new CustomException(
        null,
        "clientId must be provided. Call initialize() first."
    );
    public static final CustomException REDIRECT_URL_MISSING = new CustomException(null, "redirectUrl must be provided.");
    public static final CustomException SIGN_IN_CANCELED = new CustomException("SIGN_IN_CANCELED", "Sign in was canceled.");
    public static final CustomException SIGN_IN_FAILED = new CustomException(null, "Sign in failed.");
}
