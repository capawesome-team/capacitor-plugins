package io.capawesome.capacitorjs.plugins.liveupdate.classes;

public class CustomExceptions {

    public static final CustomException BUNDLE_ALREADY_EXISTS = new CustomException("BUNDLE_ALREADY_EXISTS", "bundle already exists.");
    public static final CustomException BUNDLE_NOT_FOUND = new CustomException("BUNDLE_NOT_FOUND", "bundle not found.");
    public static final CustomException DOWNLOAD_FAILED = new CustomException("REQUEST_FAILED", "Bundle could not be downloaded.");
    public static final CustomException HTTP_TIMEOUT = new CustomException("REQUEST_FAILED", "Request timed out.");
    public static final CustomException SYNC_IN_PROGRESS = new CustomException("SYNC_IN_PROGRESS", "Sync is already in progress.");
}
