package io.capawesome.capacitorjs.plugins.liveupdate.classes;

public class CustomExceptions {

    public static final CustomException APP_ID_MISSING = new CustomException("APP_ID_MISSING", "appId must be configured.");
    public static final CustomException BUNDLE_EXISTS = new CustomException("BUNDLE_EXISTS", "bundle already exists.");
    public static final CustomException BUNDLE_ID_MISSING = new CustomException(null, "bundleId must be provided.");
    public static final CustomException BUNDLE_INDEX_HTML_MISSING = new CustomException(
        "BUNDLE_INDEX_HTML_MISSING",
        "The bundle does not contain an index.html file."
    );
    public static final CustomException BUNDLE_NOT_FOUND = new CustomException("BUNDLE_NOT_FOUND", "bundle not found.");
    public static final CustomException CHECKSUM_CALCULATION_FAILED = new CustomException(
        "CHECKSUM_CALCULATION_FAILED",
        "Failed to calculate checksum."
    );
    public static final CustomException CHECKSUM_MISMATCH = new CustomException("CHECKSUM_MISMATCH", "Checksum mismatch.");
    public static final CustomException CUSTOM_ID_MISSING = new CustomException(null, "customId must be provided.");
    public static final CustomException DOWNLOAD_FAILED = new CustomException("DOWNLOAD_FAILED", "Bundle could not be downloaded.");
    public static final CustomException HTTP_TIMEOUT = new CustomException("HTTP_TIMEOUT", "Request timed out.");
    public static final CustomException URL_MISSING = new CustomException(null, "url must be provided.");
    public static final CustomException SIGNATURE_VERIFICATION_FAILED = new CustomException(
        "SIGNATURE_VERIFICATION_FAILED",
        "Signature verification failed."
    );
    public static final CustomException PUBLIC_KEY_INVALID = new CustomException("PUBLIC_KEY_INVALID", "Invalid public key.");
    public static final CustomException SIGNATURE_MISSING = new CustomException(
        "SIGNATURE_MISSING",
        "Bundle does not contain a signature."
    );
    public static final CustomException SYNC_IN_PROGRESS = new CustomException("SYNC_IN_PROGRESS", "Sync is already in progress.");
    public static final CustomException UNKNOWN_ERROR = new CustomException("UNKNOWN_ERROR", "An unknown error has occurred.");
}
