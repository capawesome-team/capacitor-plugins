package io.capawesome.capacitorjs.plugins.pixlive.classes;

public class CustomExceptions {

    public static final CustomException TAGS_MISSING = new CustomException(null, "tags must be provided.");
    public static final CustomException TOUR_IDS_MISSING = new CustomException(null, "tourIds must be provided.");
    public static final CustomException CONTEXT_IDS_MISSING = new CustomException(null, "contextIds must be provided.");
    public static final CustomException CONTEXT_ID_MISSING = new CustomException(null, "contextId must be provided.");
    public static final CustomException LATITUDE_MISSING = new CustomException(null, "latitude must be provided.");
    public static final CustomException LONGITUDE_MISSING = new CustomException(null, "longitude must be provided.");
    public static final CustomException MIN_LATITUDE_MISSING = new CustomException(null, "minLatitude must be provided.");
    public static final CustomException MIN_LONGITUDE_MISSING = new CustomException(null, "minLongitude must be provided.");
    public static final CustomException MAX_LATITUDE_MISSING = new CustomException(null, "maxLatitude must be provided.");
    public static final CustomException MAX_LONGITUDE_MISSING = new CustomException(null, "maxLongitude must be provided.");
    public static final CustomException LANGUAGE_MISSING = new CustomException(null, "language must be provided.");
    public static final CustomException X_MISSING = new CustomException(null, "x must be provided.");
    public static final CustomException Y_MISSING = new CustomException(null, "y must be provided.");
    public static final CustomException WIDTH_MISSING = new CustomException(null, "width must be provided.");
    public static final CustomException HEIGHT_MISSING = new CustomException(null, "height must be provided.");
    public static final CustomException ENABLED_MISSING = new CustomException(null, "enabled must be provided.");
    public static final CustomException TOP_MISSING = new CustomException(null, "top must be provided.");
    public static final CustomException BOTTOM_MISSING = new CustomException(null, "bottom must be provided.");
    public static final CustomException LEFT_MISSING = new CustomException(null, "left must be provided.");
    public static final CustomException RIGHT_MISSING = new CustomException(null, "right must be provided.");
    public static final CustomException CONTEXT_NOT_FOUND = new CustomException(null, "Context not found.");
    public static final CustomException NOT_INITIALIZED = new CustomException(null, "Plugin is not initialized.");
    public static final CustomException AR_VIEW_ALREADY_EXISTS = new CustomException(null, "AR view already exists.");
    public static final CustomException AR_VIEW_NOT_FOUND = new CustomException(null, "AR view not found.");
}
