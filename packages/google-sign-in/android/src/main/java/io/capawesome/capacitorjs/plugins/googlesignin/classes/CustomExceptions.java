package io.capawesome.capacitorjs.plugins.googlesignin.classes;

public class CustomExceptions {

    public static final CustomException ACCESS_TOKEN_MISSING = new CustomException(
        null,
        "Access token is missing from the authorization result."
    );
    public static final CustomException AUTHORIZATION_CANCELED = new CustomException(
        "AUTHORIZATION_CANCELED",
        "The user canceled the authorization flow."
    );
    public static final CustomException AUTHORIZATION_IN_PROGRESS = new CustomException(null, "An authorization is already in progress.");
    public static final CustomException CLIENT_ID_MISSING = new CustomException(null, "clientId must be provided.");
    public static final CustomException NO_CREDENTIAL_AVAILABLE = new CustomException(
        "NO_CREDENTIAL_AVAILABLE",
        "No Google account is available on this device."
    );
    public static final CustomException NOT_INITIALIZED = new CustomException(
        null,
        "Google Sign-In is not initialized. Call initialize() first."
    );
    public static final CustomException PROVIDER_CONFIGURATION_ERROR = new CustomException(
        "PROVIDER_CONFIGURATION_ERROR",
        "The credential provider is not configured correctly. Make sure that Google Play services is available and up to date."
    );
    public static final CustomException SCOPES_MISSING = new CustomException(null, "scopes must be provided.");
    public static final CustomException SIGN_IN_CANCELED = new CustomException("SIGN_IN_CANCELED", "The user canceled the sign-in flow.");
}
