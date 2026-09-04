package io.capawesome.capacitorjs.plugins.pdfannotator.classes;

public class CustomExceptions {

    public static final CustomException CANCELED = new CustomException(
        "CANCELED",
        "The user closed the viewer without saving any annotations."
    );
    public static final CustomException FILE_NOT_FOUND = new CustomException("FILE_NOT_FOUND", "The file was not found at the given path.");
    public static final CustomException LOAD_FAILED = new CustomException("LOAD_FAILED", "The PDF document could not be loaded.");
    public static final CustomException NOT_SUPPORTED = new CustomException(
        "NOT_SUPPORTED",
        "PDF annotation is not supported on this device."
    );
    public static final CustomException PATH_MISSING = new CustomException(null, "path must be provided.");
    public static final CustomException SAVE_FAILED = new CustomException("SAVE_FAILED", "The annotated PDF document could not be saved.");
}
