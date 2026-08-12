package io.capawesome.capacitorjs.plugins.clipboard.classes;

public class CustomExceptions {

    public static final CustomException CONTENT_MISSING = new CustomException(null, "One of text, html, image or url must be provided.");
    public static final CustomException EMPTY_CLIPBOARD = new CustomException("EMPTY_CLIPBOARD", "The clipboard is empty.");
    public static final CustomException READ_FAILED = new CustomException("READ_FAILED", "The clipboard content could not be read.");
    public static final CustomException WRITE_FAILED = new CustomException(
        "WRITE_FAILED",
        "The content could not be written to the clipboard."
    );
}
