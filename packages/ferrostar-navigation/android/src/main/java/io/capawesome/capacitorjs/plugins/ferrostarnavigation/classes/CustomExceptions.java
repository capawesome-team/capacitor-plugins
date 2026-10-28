package io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes;

public class CustomExceptions {

    public static final CustomException APP_NOT_IN_FOREGROUND = new CustomException(
        null,
        "The navigation can only continue in the background if it is started while the app is in the foreground."
    );
    public static final CustomException COLOR_INVALID = new CustomException(null, "color is invalid.");
    public static final CustomException COURSE_FILTERING_INVALID = new CustomException(null, "courseFiltering is invalid.");
    public static final CustomException LOCATION_PERMISSION_DENIED = new CustomException(
        "LOCATION_PERMISSION_DENIED",
        "The location permission was denied."
    );
    public static final CustomException NOT_NAVIGATING = new CustomException("NOT_NAVIGATING", "No navigation session is running.");
    public static final CustomException OSRM_RESPONSE_JSON_MISSING = new CustomException(null, "osrmResponseJson must be provided.");
    public static final CustomException ROUTE_MISSING = new CustomException(null, "osrmResponseJson contains no route.");
}
