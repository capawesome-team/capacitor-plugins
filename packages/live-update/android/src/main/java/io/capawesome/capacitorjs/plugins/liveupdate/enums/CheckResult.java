package io.capawesome.capacitorjs.plugins.liveupdate.enums;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public enum CheckResult {
    AVAILABLE("available"),
    SKIPPED("skipped"),
    UP_TO_DATE("up_to_date");

    @NonNull
    private final String value;

    CheckResult(@NonNull String value) {
        this.value = value;
    }

    @Nullable
    public static CheckResult fromValue(@Nullable String value) {
        for (CheckResult result : values()) {
            if (result.value.equals(value)) {
                return result;
            }
        }
        return null;
    }

    @NonNull
    public String getValue() {
        return value;
    }
}
