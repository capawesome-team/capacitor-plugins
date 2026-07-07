package io.capawesome.capacitorjs.plugins.phonedialer.classes;

public class CustomExceptions {

    public static final CustomException NUMBER_MISSING = new CustomException(null, "number must be provided.");

    public static final CustomException NUMBER_INVALID = new CustomException(null, "number is invalid.");

    public static final CustomException DIAL_FAILED = new CustomException("DIAL_FAILED", "The phone dialer failed to open.");
}
