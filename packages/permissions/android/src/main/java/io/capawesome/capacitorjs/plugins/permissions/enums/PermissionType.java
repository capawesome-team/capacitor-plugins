package io.capawesome.capacitorjs.plugins.permissions.enums;

import androidx.annotation.NonNull;
import io.capawesome.capacitorjs.plugins.permissions.classes.CustomException;
import io.capawesome.capacitorjs.plugins.permissions.classes.CustomExceptions;

public enum PermissionType {
    BLUETOOTH("BLUETOOTH"),
    CALENDAR("CALENDAR"),
    CAMERA("CAMERA"),
    CONTACTS("CONTACTS"),
    LOCATION("LOCATION"),
    LOCATION_ALWAYS("LOCATION_ALWAYS"),
    MICROPHONE("MICROPHONE"),
    MOTION("MOTION"),
    NOTIFICATIONS("NOTIFICATIONS"),
    PHOTOS("PHOTOS"),
    REMINDERS("REMINDERS");

    @NonNull
    private final String value;

    PermissionType(@NonNull String value) {
        this.value = value;
    }

    @NonNull
    public static PermissionType from(@NonNull String value) throws CustomException {
        for (PermissionType type : PermissionType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw CustomExceptions.PERMISSION_INVALID;
    }

    @NonNull
    public String getValue() {
        return value;
    }
}
