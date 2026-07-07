package io.capawesome.capacitorjs.plugins.clipboard.classes;

import androidx.annotation.NonNull;

public enum ClipboardContentType {
    HTML("HTML"),
    IMAGE("IMAGE"),
    TEXT("TEXT"),
    URL("URL");

    @NonNull
    private final String value;

    ClipboardContentType(@NonNull String value) {
        this.value = value;
    }

    @NonNull
    public String getValue() {
        return value;
    }
}
