package io.capawesome.capacitorjs.plugins.haptics.classes;

public class CustomExceptions {

    public static final CustomException DURATION_INVALID = new CustomException(null, "duration must be greater than 0.");
    public static final CustomException EVENTS_MISSING = new CustomException(null, "events must be provided.");
    public static final CustomException INTENSITY_INVALID = new CustomException(null, "intensity must be between 0 and 1.");
    public static final CustomException INTENSITY_MISSING = new CustomException(null, "intensity must be provided.");
    public static final CustomException PATTERN_PLAYBACK_FAILED = new CustomException(
        "PATTERN_PLAYBACK_FAILED",
        "The haptic pattern could not be played."
    );
    public static final CustomException STYLE_INVALID = new CustomException(null, "style must be one of the supported values.");
    public static final CustomException TIME_INVALID = new CustomException(null, "time must be greater than or equal to 0.");
    public static final CustomException TIME_MISSING = new CustomException(null, "time must be provided.");
    public static final CustomException TYPE_INVALID = new CustomException(null, "type must be one of the supported values.");
    public static final CustomException TYPE_MISSING = new CustomException(null, "type must be provided.");
}
