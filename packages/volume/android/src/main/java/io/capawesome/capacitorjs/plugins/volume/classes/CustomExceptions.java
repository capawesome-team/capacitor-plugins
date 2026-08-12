package io.capawesome.capacitorjs.plugins.volume.classes;

public class CustomExceptions {

    public static final CustomException DO_NOT_DISTURB_ACCESS_REQUIRED = new CustomException(
        "DO_NOT_DISTURB_ACCESS_REQUIRED",
        "Do Not Disturb access is required to change the volume of this stream."
    );
    public static final CustomException STREAM_INVALID = new CustomException(null, "stream is invalid.");
    public static final CustomException VOLUME_INVALID = new CustomException(null, "volume must be between 0 and 1.");
    public static final CustomException VOLUME_MISSING = new CustomException(null, "volume must be provided.");
}
