package io.capawesome.capacitorjs.plugins.liveupdate.classes;

import androidx.annotation.NonNull;

public class CustomExceptions {

    public static final CustomException BUNDLE_ALREADY_EXISTS = new CustomException("BUNDLE_ALREADY_EXISTS", "bundle already exists.");
    public static final CustomException BUNDLE_NOT_FOUND = new CustomException("BUNDLE_NOT_FOUND", "bundle not found.");
    public static final CustomException SYNC_IN_PROGRESS = new CustomException("SYNC_IN_PROGRESS", "Sync is already in progress.");

    public static CustomException requestFailed(@NonNull String message) {
        return new CustomException("REQUEST_FAILED", message);
    }
}
