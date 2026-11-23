package io.capawesome.capacitorjs.plugins.liveupdate.classes;

import androidx.annotation.NonNull;

public class CustomExceptions {

    private static final String CODE_REQUEST_FAILED = "REQUEST_FAILED";

    public static final CustomException BUNDLE_ALREADY_EXISTS = new CustomException("BUNDLE_ALREADY_EXISTS", "bundle already exists.");
    public static final CustomException BUNDLE_NOT_FOUND = new CustomException("BUNDLE_NOT_FOUND", "bundle not found.");
    public static final CustomException SYNC_IN_PROGRESS = new CustomException("SYNC_IN_PROGRESS", "Sync is already in progress.");

    public static boolean isRequestFailed(@NonNull Exception exception) {
        return exception instanceof CustomException && CODE_REQUEST_FAILED.equals(((CustomException) exception).getCode());
    }

    public static CustomException requestFailed(@NonNull String message) {
        return new CustomException(CODE_REQUEST_FAILED, message);
    }
}
