package io.capawesome.capacitorjs.plugins.youtubeplayer.classes;

public class CustomExceptions {

    public static final CustomException CREATE_FAILED = new CustomException("CREATE_FAILED", "The player could not be created.");
    public static final CustomException FRAME_INVALID = new CustomException(
        "FRAME_INVALID",
        "frame must contain numeric x, y, width and height values."
    );
    public static final CustomException FRAME_MISSING = new CustomException("FRAME_MISSING", "frame must be provided.");
    public static final CustomException FRAME_TOO_SMALL = new CustomException("FRAME_INVALID", "frame must be at least 200×200 pixels.");
    public static final CustomException PLAYER_ALREADY_EXISTS = new CustomException(null, "a player with the provided id already exists.");
    public static final CustomException PLAYER_ID_INVALID = new CustomException(
        "PLAYER_ID_INVALID",
        "no player found with the provided id."
    );
    public static final CustomException PLAYER_ID_MISSING = new CustomException("PLAYER_ID_MISSING", "id must be provided.");
    public static final CustomException RATE_INVALID = new CustomException(
        "RATE_INVALID",
        "rate must be one of 0.25, 0.5, 0.75, 1, 1.25, 1.5, 1.75 or 2."
    );
    public static final CustomException SECONDS_MISSING = new CustomException("SECONDS_MISSING", "seconds must be provided.");
    public static final CustomException VIDEO_ID_MISSING = new CustomException("VIDEO_ID_MISSING", "videoId must be provided.");
    public static final CustomException VOLUME_INVALID = new CustomException(
        "VOLUME_INVALID",
        "volume must be a number between 0 and 100."
    );
}
