package io.capawesome.capacitorjs.plugins.permissions.enums;

import androidx.annotation.NonNull;

public enum PermissionState {
    DENIED("denied"),
    GRANTED("granted"),
    LIMITED("limited"),
    PROMPT("prompt"),
    PROMPT_WITH_RATIONALE("prompt-with-rationale"),
    UNAVAILABLE("unavailable");

    @NonNull
    private final String value;

    PermissionState(@NonNull String value) {
        this.value = value;
    }

    @NonNull
    public String getValue() {
        return value;
    }
}
