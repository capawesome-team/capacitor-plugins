package io.capawesome.capacitorjs.plugins.pdfgenerator.classes;

public class CustomExceptions {

    public static final CustomException GENERATION_FAILED = new CustomException(
        "GENERATION_FAILED",
        "The PDF file could not be generated."
    );
    public static final CustomException HTML_MISSING = new CustomException(null, "html must be provided.");
    public static final CustomException LOAD_FAILED = new CustomException("LOAD_FAILED", "The HTML content or URL could not be loaded.");
    public static final CustomException ORIENTATION_INVALID = new CustomException(null, "orientation must be one of the supported values.");
    public static final CustomException PAGE_SIZE_INVALID = new CustomException(null, "pageSize must be one of the supported values.");
    public static final CustomException TIMEOUT = new CustomException("TIMEOUT", "The PDF generation timed out.");
    public static final CustomException URL_MISSING = new CustomException(null, "url must be provided.");
}
