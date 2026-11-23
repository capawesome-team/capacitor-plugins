package io.capawesome.capacitorjs.plugins.liveupdate.enums;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.liveupdate.LiveUpdatePlugin;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.CustomExceptions;
import net.lingala.zip4j.exception.ZipException;

public enum FailureReason {
    BUNDLE_INVALID("bundle_invalid"),
    CHECKSUM_MISMATCHED("checksum_mismatched"),
    DOWNLOAD_FAILED("download_failed"),
    READINESS_TIMED_OUT("readiness_timed_out"),
    SIGNATURE_VERIFICATION_FAILED("signature_verification_failed");

    @NonNull
    private final String value;

    FailureReason(@NonNull String value) {
        this.value = value;
    }

    /**
     * @return `null` for an exception that is not reported to the server.
     */
    @Nullable
    public static FailureReason fromException(@NonNull Exception exception) {
        if (CustomExceptions.isRequestFailed(exception)) {
            return DOWNLOAD_FAILED;
        }
        if (exception instanceof ZipException) {
            return BUNDLE_INVALID;
        }
        String message = exception.getMessage();
        if (message == null) {
            return null;
        }
        switch (message) {
            case LiveUpdatePlugin.ERROR_BUNDLE_INDEX_HTML_MISSING:
            case LiveUpdatePlugin.ERROR_MANIFEST_INVALID:
                return BUNDLE_INVALID;
            case LiveUpdatePlugin.ERROR_CHECKSUM_MISMATCH:
                return CHECKSUM_MISMATCHED;
            case LiveUpdatePlugin.ERROR_PUBLIC_KEY_INVALID:
            case LiveUpdatePlugin.ERROR_SIGNATURE_MISSING:
            case LiveUpdatePlugin.ERROR_SIGNATURE_VERIFICATION_FAILED:
                return SIGNATURE_VERIFICATION_FAILED;
            default:
                return null;
        }
    }

    @Nullable
    public static FailureReason fromValue(@Nullable String value) {
        for (FailureReason reason : values()) {
            if (reason.value.equals(value)) {
                return reason;
            }
        }
        return null;
    }

    @NonNull
    public String getValue() {
        return value;
    }
}
