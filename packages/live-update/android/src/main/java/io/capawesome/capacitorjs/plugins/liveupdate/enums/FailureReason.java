package io.capawesome.capacitorjs.plugins.liveupdate.enums;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public enum FailureReason {
    BUNDLE_INVALID("bundle_invalid"),
    CHECKSUM_MISMATCHED("checksum_mismatched"),
    DOWNLOAD_FAILED("download_failed"),
    ROLLED_BACK("rolled_back"),
    SIGNATURE_VERIFICATION_FAILED("signature_verification_failed");

    @NonNull
    private final String value;

    FailureReason(@NonNull String value) {
        this.value = value;
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
