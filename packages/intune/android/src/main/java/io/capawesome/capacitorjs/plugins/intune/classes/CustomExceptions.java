package io.capawesome.capacitorjs.plugins.intune.classes;

public class CustomExceptions {

    public static final CustomException ACCOUNT_ID_MISSING = new CustomException(null, "accountId must be provided.");
    public static final CustomException INTERACTION_CANCELED = new CustomException(
        "INTERACTION_CANCELED",
        "The user canceled the sign-in interaction."
    );
    public static final CustomException MSAL_NOT_INITIALIZED = new CustomException(
        null,
        "MSAL is not initialized. Check that the res/raw/auth_config.json file exists and that the plugin is set up correctly."
    );
    public static final CustomException NOT_ENROLLED = new CustomException(
        "NOT_ENROLLED",
        "No account with the given accountId is signed in or enrolled."
    );
    public static final CustomException SCOPES_MISSING = new CustomException(null, "scopes must be provided.");
}
