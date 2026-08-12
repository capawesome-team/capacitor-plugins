package io.capawesome.capacitorjs.plugins.intercom.classes;

public class CustomExceptions {

    public static final CustomException ANDROID_API_KEY_MISSING = new CustomException(null, "androidApiKey must be provided.");
    public static final CustomException APP_ID_MISSING = new CustomException(null, "appId must be provided.");
    public static final CustomException COMPANY_ID_MISSING = new CustomException(null, "company id must be provided.");
    public static final CustomException DATA_MISSING = new CustomException(null, "data must be provided.");
    public static final CustomException ID_MISSING = new CustomException(null, "id must be provided.");
    public static final CustomException IDS_MISSING = new CustomException(null, "ids must be provided.");
    public static final CustomException JWT_MISSING = new CustomException(null, "jwt must be provided.");
    public static final CustomException NAME_MISSING = new CustomException(null, "name must be provided.");
    public static final CustomException NOT_INITIALIZED = new CustomException(
        "NOT_INITIALIZED",
        "Intercom is not initialized. Call initialize() first."
    );
    public static final CustomException PADDING_MISSING = new CustomException(null, "padding must be provided.");
    public static final CustomException TOKEN_MISSING = new CustomException(null, "token must be provided.");
    public static final CustomException TYPE_INVALID = new CustomException(null, "type is invalid.");
    public static final CustomException USER_HASH_MISSING = new CustomException(null, "userHash must be provided.");
    public static final CustomException USER_ID_OR_EMAIL_MISSING = new CustomException(null, "userId or email must be provided.");
    public static final CustomException VISIBLE_MISSING = new CustomException(null, "visible must be provided.");
}
