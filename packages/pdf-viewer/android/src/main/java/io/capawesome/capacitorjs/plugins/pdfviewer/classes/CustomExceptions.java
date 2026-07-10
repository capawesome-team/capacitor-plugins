package io.capawesome.capacitorjs.plugins.pdfviewer.classes;

public class CustomExceptions {

    public static final CustomException FILE_NOT_FOUND = new CustomException("FILE_NOT_FOUND", "The file was not found at the given path.");
    public static final CustomException LOAD_FAILED = new CustomException("LOAD_FAILED", "The PDF document could not be loaded.");
    public static final CustomException PASSWORD_INVALID = new CustomException("PASSWORD_INVALID", "The provided password is invalid.");
    public static final CustomException PASSWORD_REQUIRED = new CustomException(
        "PASSWORD_REQUIRED",
        "The PDF document is password-protected and no password was provided."
    );
    public static final CustomException PATH_MISSING = new CustomException(null, "path must be provided.");
}
