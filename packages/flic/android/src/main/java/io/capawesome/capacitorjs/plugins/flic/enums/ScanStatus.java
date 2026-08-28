package io.capawesome.capacitorjs.plugins.flic.enums;

import androidx.annotation.NonNull;

public enum ScanStatus {
    ASK_TO_ACCEPT_PAIR_REQUEST("ASK_TO_ACCEPT_PAIR_REQUEST"),
    CONNECTED("CONNECTED"),
    DISCOVERED("DISCOVERED"),
    VERIFIED("VERIFIED");

    @NonNull
    private final String value;

    ScanStatus(@NonNull String value) {
        this.value = value;
    }

    @NonNull
    public String getValue() {
        return value;
    }
}
