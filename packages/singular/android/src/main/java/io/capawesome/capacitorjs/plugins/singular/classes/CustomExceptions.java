package io.capawesome.capacitorjs.plugins.singular.classes;

public class CustomExceptions {

    public static final CustomException AD_PLATFORM_MISSING = new CustomException(null, "adPlatform must be provided.");
    public static final CustomException AMOUNT_MISSING = new CustomException(null, "amount must be provided.");
    public static final CustomException API_KEY_MISSING = new CustomException(null, "apiKey must be provided.");
    public static final CustomException BASE_LINK_MISSING = new CustomException(null, "baseLink must be provided.");
    public static final CustomException CURRENCY_MISSING = new CustomException(null, "currency must be provided.");
    public static final CustomException CUSTOM_USER_ID_MISSING = new CustomException(null, "customUserId must be provided.");
    public static final CustomException EVENT_COULD_NOT_BE_TRACKED = new CustomException(null, "The event could not be tracked.");
    public static final CustomException GLOBAL_PROPERTY_COULD_NOT_BE_SET = new CustomException(
        null,
        "The global property could not be set."
    );
    public static final CustomException INITIALIZATION_FAILED = new CustomException(
        "INITIALIZATION_FAILED",
        "The initialization of the SDK failed."
    );
    public static final CustomException KEY_MISSING = new CustomException(null, "key must be provided.");
    public static final CustomException LIMIT_MISSING = new CustomException(null, "limit must be provided.");
    public static final CustomException NAME_MISSING = new CustomException(null, "name must be provided.");
    public static final CustomException NOT_INITIALIZED = new CustomException(
        "NOT_INITIALIZED",
        "The plugin has not been initialized yet."
    );
    public static final CustomException REFERRER_ID_MISSING = new CustomException(null, "referrerId must be provided.");
    public static final CustomException REFERRER_NAME_MISSING = new CustomException(null, "referrerName must be provided.");
    public static final CustomException REVENUE_COULD_NOT_BE_TRACKED = new CustomException(null, "The revenue could not be tracked.");
    public static final CustomException REVENUE_MISSING = new CustomException(null, "revenue must be provided.");
    public static final CustomException SECRET_MISSING = new CustomException(null, "secret must be provided.");
    public static final CustomException TOKEN_MISSING = new CustomException(null, "token must be provided.");
    public static final CustomException VALUE_MISSING = new CustomException(null, "value must be provided.");
}
