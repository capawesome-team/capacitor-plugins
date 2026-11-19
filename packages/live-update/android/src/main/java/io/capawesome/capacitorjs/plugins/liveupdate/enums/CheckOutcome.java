package io.capawesome.capacitorjs.plugins.liveupdate.enums;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public enum CheckOutcome {
    AVAILABLE("available"),
    SKIPPED("skipped"),
    UP_TO_DATE("up_to_date");

    @NonNull
    private final String value;

    CheckOutcome(@NonNull String value) {
        this.value = value;
    }

    @Nullable
    public static CheckOutcome fromValue(@Nullable String value) {
        for (CheckOutcome outcome : values()) {
            if (outcome.value.equals(value)) {
                return outcome;
            }
        }
        return null;
    }

    @NonNull
    public String getValue() {
        return value;
    }
}
