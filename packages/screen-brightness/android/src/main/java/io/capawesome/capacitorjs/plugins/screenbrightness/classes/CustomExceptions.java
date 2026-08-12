package io.capawesome.capacitorjs.plugins.screenbrightness.classes;

public class CustomExceptions {

    public static final CustomException BRIGHTNESS_INVALID = new CustomException(null, "brightness must be between 0.0 and 1.0.");
    public static final CustomException BRIGHTNESS_MISSING = new CustomException(null, "brightness must be provided.");
}
