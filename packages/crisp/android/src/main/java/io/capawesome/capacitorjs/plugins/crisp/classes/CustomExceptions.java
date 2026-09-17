package io.capawesome.capacitorjs.plugins.crisp.classes;

public class CustomExceptions {

    public static final CustomException DATA_MISSING = new CustomException(null, "data must be provided.");
    public static final CustomException ID_MISSING = new CustomException(null, "id must be provided.");
    public static final CustomException KEY_MISSING = new CustomException(null, "key must be provided.");
    public static final CustomException LOCALE_MISSING = new CustomException(null, "locale must be provided.");
    public static final CustomException NAME_MISSING = new CustomException(null, "name must be provided.");
    public static final CustomException NOT_CONFIGURED = new CustomException(
        "NOT_CONFIGURED",
        "Crisp is not configured. Call configure() first."
    );
    public static final CustomException SEGMENT_MISSING = new CustomException(null, "segment must be provided.");
    public static final CustomException TOKEN_ID_MISSING = new CustomException(null, "tokenId must be provided.");
    public static final CustomException VALUE_MISSING = new CustomException(null, "value must be provided.");
    public static final CustomException WEBSITE_ID_MISSING = new CustomException(null, "websiteId must be provided.");
}
