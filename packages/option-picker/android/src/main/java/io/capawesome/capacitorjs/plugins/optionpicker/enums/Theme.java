package io.capawesome.capacitorjs.plugins.optionpicker.enums;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public enum Theme {
    AUTO("auto"),
    DARK("dark"),
    LIGHT("light");

    @NonNull
    private final String value;

    Theme(@NonNull String value) {
        this.value = value;
    }

    @Nullable
    public static Theme fromValue(@NonNull String value) {
        for (Theme theme : values()) {
            if (theme.value.equals(value)) {
                return theme;
            }
        }
        return null;
    }
}
