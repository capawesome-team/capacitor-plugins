package io.capawesome.capacitorjs.plugins.tiktokappevents.classes;

public class CustomExceptions {

    public static final CustomException ACCESS_TOKEN_MISSING = new CustomException(null, "accessToken must be provided.");
    public static final CustomException EXTERNAL_ID_MISSING = new CustomException(null, "externalId must be provided.");
    public static final CustomException NAME_MISSING = new CustomException(null, "name must be provided.");
    public static final CustomException NOT_INITIALIZED = new CustomException(
        "NOT_INITIALIZED",
        "TikTok App Events is not initialized. Call initialize() first."
    );
    public static final CustomException TIKTOK_APP_ID_MISSING = new CustomException(null, "tiktokAppId must be provided.");
}
