package io.capawesome.capacitorjs.plugins.alarm.classes;

public class CustomExceptions {

    public static final CustomException DAYS_INVALID = new CustomException(null, "days must contain only valid weekdays.");
    public static final CustomException DURATION_INVALID = new CustomException(null, "duration must be between 1 and 86400.");
    public static final CustomException DURATION_MISSING = new CustomException(null, "duration must be provided.");
    public static final CustomException HOUR_INVALID = new CustomException(null, "hour must be between 0 and 23.");
    public static final CustomException HOUR_MISSING = new CustomException(null, "hour must be provided.");
    public static final CustomException MINUTE_INVALID = new CustomException(null, "minute must be between 0 and 59.");
    public static final CustomException MINUTE_MISSING = new CustomException(null, "minute must be provided.");
    public static final CustomException NO_CLOCK_APP = new CustomException(
        "NO_CLOCK_APP",
        "No app that can handle alarms was found on the device."
    );
}
