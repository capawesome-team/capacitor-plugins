package io.capawesome.capacitorjs.plugins.flic.enums;

import androidx.annotation.NonNull;

public enum ButtonConnectionState {
    CONNECTED("CONNECTED"),
    CONNECTING("CONNECTING"),
    DISCONNECTED("DISCONNECTED"),
    DISCONNECTING("DISCONNECTING");

    @NonNull
    private final String value;

    ButtonConnectionState(@NonNull String value) {
        this.value = value;
    }

    @NonNull
    public String getValue() {
        return value;
    }
}
