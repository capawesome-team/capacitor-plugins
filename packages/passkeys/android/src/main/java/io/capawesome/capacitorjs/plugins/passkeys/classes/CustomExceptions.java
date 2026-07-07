package io.capawesome.capacitorjs.plugins.passkeys.classes;

public class CustomExceptions {

    public static final CustomException CHALLENGE_MISSING = new CustomException(null, "challenge must be provided.");
    public static final CustomException CREATE_FAILED = new CustomException("CREATE_FAILED", "The passkey could not be created.");
    public static final CustomException GET_FAILED = new CustomException("GET_FAILED", "The passkey could not be retrieved.");
    public static final CustomException OPERATION_CANCELED = new CustomException("CANCELED", "The operation was canceled by the user.");
    public static final CustomException RP_ID_MISSING = new CustomException(null, "rp.id must be provided.");
    public static final CustomException RP_ID_TOP_LEVEL_MISSING = new CustomException(null, "rpId must be provided.");
    public static final CustomException USER_ID_MISSING = new CustomException(null, "user.id must be provided.");
}
